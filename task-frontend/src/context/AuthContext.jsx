import React, { createContext, useState, useEffect } from "react";
import { getToken, isTokenExpired, getUserFromToken, saveToken, removeToken } from "../utils/token";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => getUserFromToken(getToken()));
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const token = getToken();
    if (!token || isTokenExpired(token)) {
      setUser(null);
      removeToken();
    } else {
      setUser(getUserFromToken(token));
    }
  }, []);

  const login = (token) => {
    saveToken(token);
    setUser(getUserFromToken(token));
  };
  const logout = () => {
    removeToken();
    setUser(null);
    window.location.href = "/login";
  };

  return (
    <AuthContext.Provider value={{ user, setUser, login, logout, loading, setLoading }}>
      {children}
    </AuthContext.Provider>
  );
};
