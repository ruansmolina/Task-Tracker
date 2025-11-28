package model;

import java.time.LocalDateTime;

public class Task {
    public static Integer latestId = 0;
    private final Integer id;
    private String description;
    private Status status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Task(String description) {
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.status = Status.TO_DO;
        this.id = ++latestId;
        this.description = description;
    }

    public Task(LocalDateTime updatedAt, Integer id, String description, Status status, LocalDateTime createdAt) {
        this.updatedAt = updatedAt;
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    private void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }
    public void setStatus(Status status){
        setUpdatedAt();
        this.status = status;
    }
    public void setDescription(String desc){
        this.description = desc;
        setUpdatedAt();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
