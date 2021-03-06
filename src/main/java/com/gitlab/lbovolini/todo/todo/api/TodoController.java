package com.gitlab.lbovolini.todo.todo.api;

import com.gitlab.lbovolini.todo.common.CrudController;
import com.gitlab.lbovolini.todo.common.model.User;
import com.gitlab.lbovolini.todo.todo.service.TodoService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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

    @DeleteMapping(value = "{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> delete(@PathVariable String id, @AuthenticationPrincipal User user) {
        todoService.delete(id, user.getId());

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        throw new UnsupportedOperationException();
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

    @GetMapping(value = "{id}/download", consumes = MediaType.ALL_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object> > findByIdAndDownload(@PathVariable String id) {
        Optional<Todo> todoOptional = todoService.findById(id);

        if (todoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<List<Resource>> fileListOptional = todoService.downloadAttachments(id);

        final MultiValueMap<String, Object> response = new LinkedMultiValueMap<>();
        todoOptional.ifPresent(todo -> response.add("todo", todo));
        fileListOptional.ifPresent(files -> response.add("attachments", files.get(0)));

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Todo> save(@Valid @RequestBody Todo todo) {
        Todo savedTodo = todoService.save(todo);

        return ResponseEntity.ok(savedTodo);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> save(@Valid @RequestPart Todo todo, @RequestParam(required = false) MultipartFile attachment) {
        Todo savedTodo = Objects.nonNull(attachment)
                ? todoService.save(todo, attachment)
                : todoService.save(todo);

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

    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> update(@Valid @RequestPart Todo todo, @RequestParam(required = false) MultipartFile attachment) {
        Optional<Todo> todoOptional = Objects.nonNull(attachment)
                ? todoService.update(todo, attachment)
                : todoService.update(todo);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
