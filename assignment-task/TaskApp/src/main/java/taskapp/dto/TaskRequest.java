package taskapp.dto;


public class TaskRequest {
    private String title;
    private String description;
    private Long statusId;
    private Long priorityId;
    private Long assigneeId;

    public TaskRequest() {}
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getStatusId() { return statusId; }
    public void setStatusId(Long statusId) { this.statusId = statusId; }

    public Long getPriorityId() { return priorityId; }
    public void setPriorityId(Long priorityId) { this.priorityId = priorityId; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
}
