package com.gitlab.lbovolini.todo.todo.model;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskUnitTest {

    private String id = "5ff318937b4a37193cf7c070";
    private String name = "Teste";
    private String description = "Terminar teste Code Fiction";
    private ZonedDateTime date = ZonedDateTime.parse("2021-01-04T13:14:00.083+00:00");
    private String attachment = "";

    @Test
    void shouldCreateTask() {
        Task task = new Task(id, name, description, date);

        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
    }

    @Test
    void shouldSetTaskAttributes() {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setDate(date);

        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
    }

}