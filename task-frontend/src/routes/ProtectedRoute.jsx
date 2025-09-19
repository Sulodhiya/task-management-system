import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { getToken, isTokenExpired } from "../utils/token";

export default function ProtectedRoute({ children, roles }) {
  const { user } = useContext(AuthContext);
  const token = getToken();
  if (!token || isTokenExpired(token)) return <Navigate to="/login" />;
  if (roles && user) {
    // roles example: ['Admin', 'Super Admin']
    if (!roles.includes(user.role)) return <Navigate to="/login" />;
  }
  return children;
}
