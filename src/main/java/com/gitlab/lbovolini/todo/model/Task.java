package com.gitlab.lbovolini.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@Document
public class Task {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private ZonedDateTime date;
    private String attachment;

    public Task() {
    }

    public Task(String id, String name, String description, ZonedDateTime date, String attachment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.attachment = attachment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(date, task.date) &&
                Objects.equals(attachment, task.attachment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, date, attachment);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}
