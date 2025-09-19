import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register as registerApi } from "../services/authService";

export default function Register(){
  const [name,setName] = useState("");
  const [email,setEmail] = useState("");
  const [password,setPassword] = useState("");
  const [err,setErr] = useState("");
  const navigate = useNavigate();

  const submit = async (e)=>{
    e.preventDefault();
    try{
      await registerApi({ name, email, password });
      navigate("/login");
    }catch(error){
      setErr(error?.response?.data?.message || "Register failed");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <form onSubmit={submit} className="bg-white p-8 rounded shadow w-96">
        <h2 className="text-2xl font-semibold mb-4">Register</h2>
        {err && <div className="text-red-600 mb-2">{err}</div>}
        <label className="block mb-2">Name</label>
        <input value={name} onChange={e=>setName(e.target.value)} required className="w-full border p-2 rounded mb-3" />
        <label className="block mb-2">Email</label>
        <input value={email} onChange={e=>setEmail(e.target.value)} required className="w-full border p-2 rounded mb-3" />
        <label className="block mb-2">Password</label>
        <input type="password" value={password} onChange={e=>setPassword(e.target.value)} required className="w-full border p-2 rounded mb-4" />
        <button className="w-full bg-green-600 text-white py-2 rounded">Register</button>
      </form>
    </div>
  );
}
