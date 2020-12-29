package com.gitlab.lbovolini.todolist.controller;

import com.gitlab.lbovolini.todolist.model.Task;
import com.gitlab.lbovolini.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Consome: MediaType.APPLICATION_JSON
 * Produz: MediaType.APPLICATION_JSON
 */
@RestController
@RequestMapping(path = "tasks",
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
     * @return Retorna ResponseEntity com HttpStatus 204, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
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

        if (taskList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(taskList);
    }

    /**
     * Salva a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com a tarefa salva e com HttpStatus 200, ou HttpStatus 409 se a tarefa informada já existe na base de dados.
     */
    @Override
    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task task) {
        Task savedTask = taskService.save(task);

        return ResponseEntity.ok(savedTask);
    }

    /**
     * Atualiza a tarefa na base de dados.
     * @param task
     * @return Retorna ResponseEntity com HttpStatus 200, ou HttpStatus 404 se a tarefa com o id informado não for encontrada.
     */
    @Override
    @PutMapping
    public ResponseEntity update(@RequestBody Task task) {
        taskService.update(task);

        return ResponseEntity.ok().build();
    }
}
