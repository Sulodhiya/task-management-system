import api from "../api/axios";

// Register user
export const register = async (payload) => {
  try {
    const response = await api.post("/auth/register", payload);
    return response.data;
  } catch (error) {
    console.error(
      "Registration failed:",
      error.response?.data || error.message
    );
    throw error.response?.data || { message: "Something went wrong" };
  }
};

// Login user
export const login = async (payload) => {
  try {
    const response = await api.post("/auth/login", payload);
    // save token locally if exists
    if (response.data.token) localStorage.setItem("token", response.data.token);
    return response.data;
  } catch (error) {
    console.error("Login failed:", error.response?.data || error.message);
    throw error.response?.data || { message: "Something went wrong" };
  }
};

// Get all users (protected route)
export const getUsers = async () => {
  try {
    const response = await api.get("/users");
    return response.data;
  } catch (error) {
    console.error(
      "Fetching users failed:",
      error.response?.data || error.message
    );
    throw error.response?.data || { message: "Something went wrong" };
  }
};
