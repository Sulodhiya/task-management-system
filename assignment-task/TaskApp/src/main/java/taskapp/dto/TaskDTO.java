package taskapp.dto;

import java.time.LocalDateTime;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private StatusDTO status;
    private PriorityDTO priority;
    private UserDTO assignee;
    private UserDTO creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskDTO() {}

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public StatusDTO getStatus() { return status; }
    public void setStatus(StatusDTO status) { this.status = status; }

    public PriorityDTO getPriority() { return priority; }
    public void setPriority(PriorityDTO priority) { this.priority = priority; }

    public UserDTO getAssignee() { return assignee; }
    public void setAssignee(UserDTO assignee) { this.assignee = assignee; }

    public UserDTO getCreator() { return creator; }
    public void setCreator(UserDTO creator) { this.creator = creator; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
