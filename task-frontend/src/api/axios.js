import axios from "axios";

const baseURL = import.meta.env.VITE_API_URL || "http://localhost:3030/api";

const api = axios.create({
  baseURL,
  withCredentials: false, // keep false for JWT in headers
  headers: {
    "Content-Type": "application/json",
  },
});

// attach token if exists
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// handle 401 globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login"; // optional redirect
    }
    return Promise.reject(error);
  }
);

export default api;
