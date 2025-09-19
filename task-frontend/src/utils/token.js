import { jwtDecode } from "jwt-decode";

export const saveToken = (token) => localStorage.setItem("token", token);
export const removeToken = () => localStorage.removeItem("token");
export const getToken = () => localStorage.getItem("token");

export const isTokenExpired = (token) => {
  if (!token) return true;
  try {
    const { exp } = jwtDecode(token);
    // exp is in seconds
    return Date.now() > exp * 1000;
  } catch (e) {
    return true;
  }
};

export const getUserFromToken = (token) => {
  if (!token) return null;
  try {
    return jwtDecode(token);
  } catch {
    return null;
  }
};
