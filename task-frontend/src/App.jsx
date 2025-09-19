import React from "react";
import { Outlet } from "react-router-dom";

export default function App(){
  return (
    <div className="min-h-screen min-h-screen bg-gray-50 text-gray-800">
      <Outlet/>
    </div>
  );
}
