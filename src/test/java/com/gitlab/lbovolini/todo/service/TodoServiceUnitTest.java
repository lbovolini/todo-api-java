package com.gitlab.lbovolini.todo.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

@ExtendWith(MockitoExtension.class)
class TodoServiceUnitTest {

    private String id = "5ff318937b4a37193cf7c070";
    private String name = "Teste";
    private String description = "Terminar teste Code Fiction";
    private ZonedDateTime date = ZonedDateTime.parse("2021-01-04T13:14:00.083+00:00");
    private String attachment = "";
    //private Task task = new Task(id, name, description, date, null);

   /* @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(id);
        todoService.delete(id);

        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());
        List<Task> taskList = todoService.findAll();

        assertEquals(List.of(), taskList);
    }

    @Test
    void shouldFindTaskById() {
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        Optional<Task> taskOptional = todoService.findById(id);

        assertEquals(task, taskOptional.get());
    }

    @Test
    void shouldSaveTask() {
        when(taskRepository.save(task)).thenReturn(task);
        Task savedTask = todoService.save(task);

        assertEquals(task, savedTask);
    }

    @Test
    void shouldUpdateTask() {
        when(taskRepository.update(task)).thenReturn(Optional.of(task));
        Optional<Task> taskOptional = todoService.update(task);

        assertEquals(task, taskOptional.get());
    }

    @Test
    void shouldNotFoundTaskToUpdate() {
        when(taskRepository.update(task)).thenReturn(Optional.empty());
        Optional<Task> taskOptional = todoService.update(task);

        assertEquals(Optional.empty(), taskOptional);
    }*/
}
