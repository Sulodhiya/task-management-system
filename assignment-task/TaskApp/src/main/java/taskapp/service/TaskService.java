package taskapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskapp.entity.*;
import taskapp.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       StatusRepository statusRepository,
                       PriorityRepository priorityRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasks(Long statusId, Long assigneeId) {
        if (statusId != null && assigneeId != null) {
            return taskRepository.findByStatusIdAndAssigneeId(statusId, assigneeId);
        } else if (statusId != null) {
            return taskRepository.findByStatusId(statusId);
        } else if (assigneeId != null) {
            return taskRepository.findByAssigneeId(assigneeId);
        } else {
            return taskRepository.findAll();
        }
    }

    @Transactional
    public Task createTask(Task task, Long creatorId) {
        if (task.getStatus() != null && task.getStatus().getId() != null) {
            Status status = statusRepository.findById(task.getStatus().getId())
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            task.setStatus(status);
        }
        if (task.getPriority() != null && task.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(task.getPriority().getId())
                    .orElseThrow(() -> new RuntimeException("Priority not found"));
            task.setPriority(priority);
        }
        if (task.getAssignee() != null && task.getAssignee().getId() != null) {
            User assignee = userRepository.findById(task.getAssignee().getId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);
        }
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));
        task.setCreator(creator);

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task updated) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updated.getTitle() != null) {
            existing.setTitle(updated.getTitle());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getStatus() != null && updated.getStatus().getId() != null) {
            Status status = statusRepository.findById(updated.getStatus().getId())
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            existing.setStatus(status);
        }
        if (updated.getPriority() != null && updated.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(updated.getPriority().getId())
                    .orElseThrow(() -> new RuntimeException("Priority not found"));
            existing.setPriority(priority);
        }
        if (updated.getAssignee() != null && updated.getAssignee().getId() != null) {
            User assignee = userRepository.findById(updated.getAssignee().getId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            existing.setAssignee(assignee);
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task transitionStatus(Long id, Long newStatusId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Status newStatus = statusRepository.findById(newStatusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        String from = task.getStatus() != null ? task.getStatus().getName() : null;
        String to = newStatus.getName();

        if (from == null) {
            task.setStatus(newStatus);
        } else if (from.equals("ToDo") && to.equals("In_Progress")) {
            task.setStatus(newStatus);
        } else if (from.equals("In_Progress") && to.equals("Done")) {
            task.setStatus(newStatus);
        } else if (from.equals(to)) {
        } else {
            throw new RuntimeException("Invalid status transition from " + from + " to " + to);
        }

        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
}
