package com.gitlab.lbovolini.todo.todo.api;

import com.gitlab.lbovolini.todo.controller.CrudController;
import com.gitlab.lbovolini.todo.todo.TodoService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Consome: MediaType.APPLICATION_JSON
 * Produz: MediaType.APPLICATION_JSON
 */
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

    /**
     * Remove a tarefa com o id informado.
     * @param id
     * @return Retorna ResponseEntity com HttpStatus 204.
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza a busca da tarefa pelo id informado.
     * @param id
     * @return Retorna ResponseEntity com a tarefa e com HttpStatus 200, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
     */
    @Override
    @GetMapping("{id}")
    public ResponseEntity<Todo> findById(@PathVariable String id) {
        Optional<Todo> todoOptional = todoService.findById(id);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a busca de todas as tarefas.
     * @return Retorna ResponseEntity com a lista de todas as tarefas e com HttpStatus 200.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
        List<Todo> todoList = todoService.findAll();

        return ResponseEntity.ok(todoList);
    }

    @Override
    public ResponseEntity<Todo> save(Todo todo) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Todo todo) {
        return null;
    }

    /**
     * Salva a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com a tarefa salva e com HttpStatus 200, ou HttpStatus 409 se a tarefa informada já existe na base de dados.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> save(@Valid @RequestPart Todo todo, @RequestParam MultipartFile attachment) {
        Todo savedTodo = todoService.save(todo, attachment);

        return ResponseEntity.ok(savedTodo);
    }

    /**
     * Atualiza a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com a tarefa atualizada e com HttpStatus 200, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
     */
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Todo> update(@Valid @RequestPart Todo todo, @RequestParam MultipartFile attachment) {
        Optional<Todo> todoOptional = todoService.update(todo, attachment);

        return todoOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
