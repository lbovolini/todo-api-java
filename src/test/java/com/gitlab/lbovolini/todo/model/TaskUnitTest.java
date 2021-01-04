package com.gitlab.lbovolini.todo.model;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskUnitTest {

    private String id = "5ff318937b4a37193cf7c070";
    private String name = "Teste";
    private String description = "Terminar teste Code Fiction";
    private ZonedDateTime date = ZonedDateTime.parse("2021-01-04T13:14:00.083+00:00");
    private String attachment = "";

    @Test
    void shouldCreateTask() {
        Task task = new Task(id, name, description, date, attachment);

        assertEquals(id, task.getId());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
        assertEquals(attachment, task.getAttachment());
    }

    @Test
    void shouldSetTaskAttributes() {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setDate(date);
        task.setAttachment(attachment);

        assertEquals(id, task.getId());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
        assertEquals(attachment, task.getAttachment());
    }

}