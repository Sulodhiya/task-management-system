import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import TaskCard from "../components/TaskCard";
import TaskModal from "../components/TaskModal";
import { fetchTasks, createTask, updateTask, deleteTask, changeStatus } from "../services/taskService";
import { getUsers } from "../services/authService";

export default function Dashboard(){
  const [tasks, setTasks] = useState([]);
  const [users, setUsers] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [priorities, setPriorities] = useState([]);
  const [statuses, setStatuses] = useState([]);

  const load = async () => {
    try {
      const t = await fetchTasks();
      setTasks(t);
      const u = await getUsers();
      setUsers(u);
      // If backend exposes /priorities and /statuses endpoints, fetch them:
      const pr = await fetch("/api/priorities").then(r=>r.json()).catch(()=>[]);
      const st = await fetch("/api/statuses").then(r=>r.json()).catch(()=>[]);
      setPriorities(pr);
      setStatuses(st);
    } catch (e) { console.error(e); }
  };

  useEffect(()=> { load(); }, []);

  const onSave = async (payload) => {
    try {
      if (payload.id) await updateTask(payload.id, payload);
      else await createTask(payload);
      setModalOpen(false); setEditing(null);
      await load();
    } catch (e) { console.error(e); }
  };

  const onDelete = async (id) => {
    if (!confirm("Delete task?")) return;
    await deleteTask(id);
    await load();
  };

  const onStatusChange = async (task, newStatusName) => {
    // convert name to id by statuses list (simple mapping)
    const sObj = statuses.find(s => s.name.toLowerCase() === newStatusName.toLowerCase());
    if (!sObj) {
      // fallback: change by server rules — here we attempt using name
      alert("Status mapping not found on frontend. Ensure /statuses exists.");
      return;
    }
    await changeStatus(task.id, sObj.id);
    await load();
  };

  const byStatus = (name) => tasks.filter(t => (t.status?.name || "").toLowerCase() === name.toLowerCase());

  return (
    <div>
      <Navbar />
      <div className="p-6">
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-semibold">Dashboard</h1>
          <div>
            <button onClick={() => { setEditing(null); setModalOpen(true); }} className="px-4 py-2 bg-green-600 text-white rounded">Create Task</button>
          </div>
        </div>

        <div className="grid grid-cols-3 gap-4">
          <div>
            <h2 className="font-semibold mb-2">ToDo</h2>
            {byStatus("ToDo").map(t => <TaskCard key={t.id} task={t} onEdit={(tk)=>{setEditing(tk); setModalOpen(true)}} onDelete={onDelete} onStatusChange={onStatusChange} />)}
          </div>

          <div>
            <h2 className="font-semibold mb-2">In_Progress</h2>
            {byStatus("In_Progress").map(t => <TaskCard key={t.id} task={t} onEdit={(tk)=>{setEditing(tk); setModalOpen(true)}} onDelete={onDelete} onStatusChange={onStatusChange} />)}
          </div>

          <div>
            <h2 className="font-semibold mb-2">Done</h2>
            {byStatus("Done").map(t => <TaskCard key={t.id} task={t} onEdit={(tk)=>{setEditing(tk); setModalOpen(true)}} onDelete={onDelete} onStatusChange={onStatusChange} />)}
          </div>
        </div>
      </div>

      <TaskModal open={modalOpen} onClose={() => setModalOpen(false)} onSave={onSave} task={editing} users={users} priorities={priorities} statuses={statuses} />
    </div>
  );
}
