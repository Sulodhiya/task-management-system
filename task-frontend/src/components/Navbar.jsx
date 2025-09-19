import React, { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function Navbar(){
  const { user, logout } = useContext(AuthContext);
  return (
    <div className="flex items-center justify-between p-4 bg-white shadow">
      <div className="text-xl font-bold">Task Manager</div>
      <div className="flex items-center gap-4">
        {user && <div className="text-sm">Hi, {user.name || user.email}</div>}
        {user && <button onClick={logout} className="px-3 py-1 bg-red-500 text-white rounded">Logout</button>}
      </div>
    </div>
  );
}
