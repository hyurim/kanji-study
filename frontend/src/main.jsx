import React from "react";
import { createRoot } from "react-dom/client";
// import { BrowserRouter } from "react-router-dom"; // 서버에서 URL 처리
import { HashRouter } from "react-router-dom";
import App from "./App";
import { AuthProvider } from "./auth/AuthProvider";
import GlobalStyle from "./styles/GlobalStyle";

createRoot(document.getElementById("root")).render(
  <React.StrictMode>
	<HashRouter>
    {/* <BrowserRouter basename={import.meta.env.BASE_URL || "/"}> */}
	  	<AuthProvider>
		  	<GlobalStyle />
			  <App />
		  </AuthProvider>
    {/* </BrowserRouter> */}
	</HashRouter>
  </React.StrictMode>
);