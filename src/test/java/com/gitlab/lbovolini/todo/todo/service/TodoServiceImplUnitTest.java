package com.gitlab.lbovolini.todo.todo.service;

import com.gitlab.lbovolini.todo.common.model.User;
import com.gitlab.lbovolini.todo.todo.model.Attachment;
import com.gitlab.lbovolini.todo.todo.model.Task;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import com.gitlab.lbovolini.todo.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Task taskOne = new Task("5ff318937b4a37193cf7c070", "Teste", "Terminar teste Code Fiction", ZonedDateTime.parse("2021-01-04T13:14:00.083+00:00"));

    private Attachment attachmentOne = new Attachment("/tmp/file.txt");
    private Attachment attachmentTwo = new Attachment("/tmp/anotherFile.txt");

    private String id = "5ff4bf7b55d86d19c087d15e";
    private String userId;
    private List<Task> tasks = List.of(taskOne);
    private List<Attachment> attachments = List.of(attachmentOne, attachmentTwo);

    private Todo todo = new Todo(id, userId, tasks, attachments);

    @Test
    void shouldDeleteTodoById() {
        doNothing().when(todoRepository).delete(id);
        todoService.delete(id);

        verify(todoRepository, times(1)).delete(id);
    }

    @Test
    void shouldFindTodoById() {
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
        Optional<Todo> todoOptional = todoService.findById(id);

        assertEquals(todo, todoOptional.get());
    }

    @Test
    void shouldSaveTodo() {
        when(todoRepository.save(todo)).thenReturn(todo);
        Todo savedTodo = todoService.save(todo);

        assertEquals(todo, savedTodo);
    }

    @Test
    void shouldUpdateTodo() {
        when(todoRepository.update(todo)).thenReturn(Optional.of(todo));
        Optional<Todo> todoOptional = todoService.update(todo);

        assertEquals(todo, todoOptional.get());
    }

    @Test
    void shouldNotFoundTodoToUpdate() {
        when(todoRepository.update(todo)).thenReturn(Optional.empty());
        Optional<Todo> todoOptional = todoService.update(todo);

        assertEquals(Optional.empty(), todoOptional);
    }
}
