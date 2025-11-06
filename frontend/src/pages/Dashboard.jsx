import React from "react";
import { useAuth } from "../auth/useAuth";

export default function Dashboard(){
  const { user } = useAuth();
  return (
    <div style={{padding:24}}>
      <h1 style={{marginTop:0}}>Dashboard</h1>
      {user ? (
        <p>Welcome, <b>{user?.name || user?.loginId}</b>!</p>
      ) : (
        <p>로그인이 필요합니다.</p>
      )}
    </div>
  );
}