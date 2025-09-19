import React, { useState, useEffect } from "react";

export default function TaskModal({ open, onClose, onSave, task = {}, users = [], priorities = [], statuses = [] }){
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  const [assigneeId, setAssigneeId] = useState("");
  const [priorityId, setPriorityId] = useState("");
  const [statusId, setStatusId] = useState("");

  useEffect(()=> {
    setTitle(task.title || "");
    setDesc(task.description || "");
    setAssigneeId(task.assignee?.id || "");
    setPriorityId(task.priority?.id || "");
    setStatusId(task.status?.id || "");
  }, [task]);

  if (!open) return null;
  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded w-96">
        <h3 className="text-lg font-semibold mb-3">{task.id ? "Edit Task" : "Create Task"}</h3>
        <input value={title} onChange={e=>setTitle(e.target.value)} placeholder="Title" className="w-full border p-2 rounded mb-2" />
        <textarea value={desc} onChange={e=>setDesc(e.target.value)} placeholder="Description" className="w-full border p-2 rounded mb-2" />
        <select value={assigneeId} onChange={e=>setAssigneeId(e.target.value)} className="w-full border p-2 rounded mb-2">
          <option value="">Unassigned</option>
          {users.map(u => <option key={u.id} value={u.id}>{u.name || u.email}</option>)}
        </select>
        <select value={priorityId} onChange={e=>setPriorityId(e.target.value)} className="w-full border p-2 rounded mb-2">
          <option value="">Select priority</option>
          {priorities.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
        </select>
        <div className="flex gap-2 justify-end">
          <button onClick={onClose} className="px-3 py-1 border rounded">Cancel</button>
          <button onClick={() => onSave({ id: task.id, title, description: desc, assigneeId, priorityId, statusId })} className="px-3 py-1 bg-blue-600 text-white rounded">Save</button>
        </div>
      </div>
    </div>
  );
}
