import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../auth/useAuth";

// const Protected = (children) => {

// }
export default function Protected({ children }){
  const { user, loading } = useAuth();
  const loc = useLocation();
  if (loading) return <div style={{padding:24}}>Loading...</div>;
  if (!user) return <Navigate to="/login" state={{ from: loc }} replace />;
  return children;
}