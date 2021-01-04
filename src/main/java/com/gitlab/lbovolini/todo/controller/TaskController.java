package com.gitlab.lbovolini.todo.controller;

import com.gitlab.lbovolini.todo.model.Task;
import com.gitlab.lbovolini.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Consome: MediaType.APPLICATION_JSON
 * Produz: MediaType.APPLICATION_JSON
 */
@RestController
@RequestMapping(path = "api/v1/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController implements CrudController<Task> {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Remove a tarefa com o id informado.
     * @param id
     * @return Retorna ResponseEntity com HttpStatus 204.
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        taskService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza a busca da tarefa pelo id informado.
     * @param id
     * @return Retorna ResponseEntity com a tarefa e com HttpStatus 200, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
     */
    @Override
    @GetMapping("{id}")
    public ResponseEntity<Task> findById(@PathVariable String id) {
        Optional<Task> taskOptional = taskService.findById(id);

        return taskOptional.stream()
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
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = taskService.findAll();

        return ResponseEntity.ok(taskList);
    }

    /**
     * Salva a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com a tarefa salva e com HttpStatus 200, ou HttpStatus 409 se a tarefa informada já existe na base de dados.
     */
    @Override
    @PostMapping
    public ResponseEntity<Task> save(@Valid @RequestBody Task task) {
        Task savedTask = taskService.save(task);

        return ResponseEntity.ok(savedTask);
    }

    /**
     * Atualiza a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com a tarefa atualizada e com HttpStatus 200, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
     */
    @Override
    @PutMapping
    public ResponseEntity<Task> update(@Valid @RequestBody Task task) {
        Optional<Task> taskOptional = taskService.update(task);

        return taskOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
