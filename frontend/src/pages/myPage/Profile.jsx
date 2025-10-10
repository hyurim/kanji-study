import React, { useEffect, useState } from "react";
import api from "../../api/axios";

export default function Profile(){
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get("/api/auth/me");
        setProfile(data);
      } catch (e) {
        setError(e?.response?.data?.message || e?.message || "Failed to load profile");
      }
    })();
  }, []);

  if (error) return <div style={{padding:24,color:'#b91c1c'}}>{error}</div>;
  if (!profile) return <div style={{padding:24}}>Loading...</div>;

  return (
    <div style={{padding:24}}>
      <h1 style={{marginTop:0}}>Profile</h1>
      <pre style={{background:'#f9fafb',padding:12,borderRadius:8}}>{JSON.stringify(profile, null, 2)}</pre>
    </div>
  );
}
