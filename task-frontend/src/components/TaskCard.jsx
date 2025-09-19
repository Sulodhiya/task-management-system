import React from "react";

export default function TaskCard({ task, onEdit, onDelete, onStatusChange }) {
  return (
    <div className="bg-white p-3 rounded shadow mb-3">
      <div className="flex justify-between items-start">
        <div>
          <h3 className="font-semibold">{task.title}</h3>
          <p className="text-sm text-gray-600">{task.description}</p>
        </div>
        <div className="text-sm text-gray-500">{task.priority?.name}</div>
      </div>
      <div className="flex items-center justify-between mt-3">
        <div className="text-xs text-gray-500">Assigned: {task.assignee?.name || "Unassigned"}</div>
        <div className="flex gap-2">
          <button onClick={() => onEdit(task)} className="text-sm px-2 py-1 border rounded">Edit</button>
          <button onClick={() => onDelete(task.id)} className="text-sm px-2 py-1 border rounded text-red-600">Delete</button>
        </div>
      </div>
      <div className="mt-2 flex gap-2">
        <button onClick={() => onStatusChange(task, "ToDo")} className="text-xs border px-2 py-1 rounded">ToDo</button>
        <button onClick={() => onStatusChange(task, "In_Progress")} className="text-xs border px-2 py-1 rounded">In_Progress</button>
        <button onClick={() => onStatusChange(task, "Done")} className="text-xs border px-2 py-1 rounded">Done</button>
      </div>
    </div>
  );
}
