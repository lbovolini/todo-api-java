package com.gitlab.lbovolini.todo.todo.api;

import com.gitlab.lbovolini.todo.common.CrudController;
import com.gitlab.lbovolini.todo.todo.service.TodoService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/todos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController implements CrudController<Todo> {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(value = "{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Todo> findById(@PathVariable String id) {
        Optional<Todo> todoOptional = todoService.findById(id);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<Todo>> findAll() {
        List<Todo> todoList = todoService.findAll();

        return ResponseEntity.ok(todoList);
    }

    @Override
    @PostMapping
    public ResponseEntity<Todo> save(@Valid @RequestBody Todo todo) {
        Todo savedTodo = todoService.save(todo);

        return ResponseEntity.ok(savedTodo);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> save(@Valid @RequestPart Todo todo, @RequestParam MultipartFile attachment) {
        Todo savedTodo = todoService.save(todo, attachment);

        return ResponseEntity.ok(savedTodo);
    }

    @Override
    @PutMapping
    public ResponseEntity<Todo> update(@Valid @RequestBody Todo todo) {
        Optional<Todo> todoOptional = todoService.update(todo);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> update(@Valid @RequestPart Todo todo, @RequestParam MultipartFile attachment) {
        Optional<Todo> todoOptional = todoService.update(todo, attachment);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
