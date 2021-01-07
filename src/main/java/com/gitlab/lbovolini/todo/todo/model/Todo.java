package com.gitlab.lbovolini.todo.todo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Document
public class Todo {

    @Id
    private String id;
    @NotBlank
    private String userId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Task> tasks;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Attachment> attachments;

    public Todo() {}

    public Todo(String id, String userId, List<Task> tasks, List<Attachment> attachments) {
        this.id = id;
        this.userId = userId;
        this.tasks = tasks;
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(userId, todo.userId) && Objects.equals(tasks, todo.tasks) && Objects.equals(attachments, todo.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, tasks, attachments);
    }
}
