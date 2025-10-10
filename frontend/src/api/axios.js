import axios from "axios";

export const API_BASE = import.meta.env.DEV
  ? "/api"
  : import.meta.env.VITE_API_BASE ||
    "https://kanji-proxy.yimjh2309.workers.dev";

const api = axios.create({
  baseURL: API_BASE,
  withCredentials: true,
});

let getAccessToken = () => null;
export const setAccessTokenGetter = (fn) => {
  getAccessToken = fn;
};

let onTokenRefreshed = () => {};
export const setOnTokenRefreshed = (fn) => {
  onTokenRefreshed = fn;
};

api.interceptors.request.use((config) => {
  const token = getAccessToken?.();
  if (token) config.headers["Authorization"] = `Bearer ${token}`;
  return config;
});

let isRefreshing = false;
let queue = [];

const flushQueue = (error, token = null) => {
  queue.forEach(({ resolve, reject }) => {
    if (error) reject(error);
    else resolve(token);
  });
  queue = [];
};

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const original = error.config;
    const is401 = error?.response?.status === 401;
    const isRefreshCall = original?.url?.includes("/token/refresh");

    const hasRefreshCookie =
      typeof document !== "undefined" &&
      document.cookie.includes("refreshToken=");

    if (is401 && !original?._retry && !isRefreshCall) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          queue.push({ resolve, reject });
        }).then((token) => {
          original.headers["Authorization"] = `Bearer ${token}`;
          return api(original);
        });
      }

      original._retry = true;
      isRefreshing = true;

      try {
        const { data } = await api.post("/token/refresh", {});
        const newToken = data?.access_token || data?.accessToken || data?.token;
        if (!newToken) throw new Error("No access token in refresh response");
        onTokenRefreshed?.(newToken);

        original.headers = original.headers || {};
        original.headers["Authorization"] = `Bearer ${newToken}`;
        flushQueue(null, newToken);
        isRefreshing = false;
        return api(original);
      } catch (e) {
        flushQueue(e, null);
        isRefreshing = false;
        return Promise.reject(e);
      }
    }

    return Promise.reject(error);
  }
);

export default api;
