import api from "../api/axios";

export const fetchTasks = (filters = {}) =>
  api.get("/tasks", { params: filters }).then((r) => r.data);

export const createTask = (payload) =>
  api.post("/tasks", payload).then((r) => r.data);
export const updateTask = (id, payload) =>
  api.put(`/tasks/${id}`, payload).then((r) => r.data);
export const deleteTask = (id) =>
  api.delete(`/tasks/${id}`).then((r) => r.data);
export const changeStatus = (id, statusId) =>
  api.put(`/tasks/${id}`, { statusId }).then((r) => r.data);
