package com.gitlab.lbovolini.todo.service;

import com.gitlab.lbovolini.todo.model.Task;
import com.gitlab.lbovolini.todo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {

    private String id = "5ff318937b4a37193cf7c070";
    private String name = "Teste";
    private String description = "Terminar teste Code Fiction";
    private ZonedDateTime date = ZonedDateTime.parse("2021-01-04T13:14:00.083+00:00");
    private String attachment = "";
    private Task task = new Task(id, name, description, date, attachment);

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(id);
        taskService.delete(id);

        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());
        List<Task> taskList = taskService.findAll();

        assertEquals(List.of(), taskList);
    }

    @Test
    void shouldFindTaskById() {
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        Optional<Task> taskOptional = taskService.findById(id);

        assertEquals(task, taskOptional.get());
    }

    @Test
    void shouldSaveTask() {
        when(taskRepository.save(task)).thenReturn(task);
        Task savedTask = taskService.save(task);

        assertEquals(task, savedTask);
    }

    @Test
    void shouldUpdateTask() {
        when(taskRepository.update(task)).thenReturn(Optional.of(task));
        Optional<Task> taskOptional = taskService.update(task);

        assertEquals(task, taskOptional.get());
    }

    @Test
    void shouldNotFoundTaskToUpdate() {
        when(taskRepository.update(task)).thenReturn(Optional.empty());
        Optional<Task> taskOptional = taskService.update(task);

        assertEquals(Optional.empty(), taskOptional);
    }
}
