import React, { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login as loginApi } from "../services/authService";
import { AuthContext } from "../context/AuthContext";

export default function Login(){
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [err, setErr] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await loginApi({ email, password });
      // expected: data.token
      if (data.token) {
        login(data.token);
        navigate("/dashboard");
      } else {
        setErr("Invalid response from server.");
      }
    } catch (error) {
      setErr(error?.response?.data?.message || "Login failed");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded shadow w-96">
        <h2 className="text-2xl font-semibold mb-4">Login</h2>
        {err && <div className="text-red-600 mb-2">{err}</div>}
        <label className="block mb-2">Email</label>
        <input value={email} onChange={e=>setEmail(e.target.value)} required className="w-full border p-2 rounded mb-3" />
        <label className="block mb-2">Password</label>
        <input type="password" value={password} onChange={e=>setPassword(e.target.value)} required className="w-full border p-2 rounded mb-4" />
        <button className="w-full bg-blue-600 text-white py-2 rounded">Login</button>
        <p className="mt-3 text-sm">No account? <Link to="/register" className="text-blue-600">Register</Link></p>
      </form>
    </div>
  );
}
