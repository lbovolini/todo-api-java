package com.gitlab.lbovolini.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.time.format.FormatStyle;
import java.util.Objects;

public class Task {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private ZonedDateTime date;

    public Task() {
    }

    public Task(String id, String name, String description, ZonedDateTime date) {
        this.name = name;
        this.description = description;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date);
    }
}
