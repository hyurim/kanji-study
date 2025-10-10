import React, { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import api, { setAccessTokenGetter, setOnTokenRefreshed, API_BASE } from "../api/axios";

const AuthCtx = createContext(null);

export function AuthProvider({ children }){
  const [accessToken, setAccessToken] = useState(() => sessionStorage.getItem("access_token") || "");
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => { setAccessTokenGetter(() => accessToken);
	setOnTokenRefreshed((t) => setAccessToken(t));
 }, [accessToken]);

  useEffect(() => {
    if (accessToken) sessionStorage.setItem("access_token", accessToken);
    else sessionStorage.removeItem("access_token");
  }, [accessToken]);

  const fetchMe = useCallback(async () => {
    try {
		const { data } = await api.get("/me");
      setUser(data);
      return data;
    } catch {
      setUser(null);
      return null;
    }
  }, []);

  useEffect(() => {
    (async () => {
      try {
        if (accessToken) {
          await fetchMe();
        } else {
			const hasRefreshCookie =
			typeof document !== "undefined" &&
			document.cookie.includes("refreshToken=");
			if (!hasRefreshCookie) {
			return;
			}
			const { data } = await api.post("/token/refresh", {});
          const token = data?.access_token || data?.accessToken || data?.token;
          if (token) {
            setAccessToken(token);
            await fetchMe();
          }
        }
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const login = useCallback(async (loginId, password) => {
    const { data } = await api.post("/login", { loginId, password });
    const token = data?.access_token || data?.accessToken || data?.token;
    if (!token) throw new Error("No access token returned");
    setAccessToken(token);
    const profile = await fetchMe();
    return profile;
  }, [fetchMe]);

  const signup = useCallback(async (payload) => {
    await api.post("/signup", payload);
    return true;
  }, []);

  const logout = useCallback(async () => {
    try { await api.post("/logout", {}); } catch {}
    setAccessToken("");
    setUser(null);
  }, []);

  const value = useMemo(() => ({ user, accessToken, login, signup, logout, loading, API_BASE }), [user, accessToken, login, signup, logout, loading]);
  return <AuthCtx.Provider value={value}>{children}</AuthCtx.Provider>;
}

export const useAuth = () => useContext(AuthCtx);
