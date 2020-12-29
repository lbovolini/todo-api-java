package com.gitlab.lbovolini.todolist.controller;

import com.gitlab.lbovolini.todolist.model.Task;
import com.gitlab.lbovolini.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "tasks")
public class TaskController implements CrudController<Task> {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        taskService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza a busca da Task por id.
     * @param id
     * @return ResponseEntity com a Task e HttpStatus 200, ou HttpStatus 404 se a Task com o id informado não for encontrada.
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

    @Override
    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = taskService.findAll();

        if (taskList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(taskList);
    }

    @Override
    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task task) {
        Task savedTask = taskService.save(task);

        return ResponseEntity.ok(savedTask);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@RequestBody Task task) {
        taskService.update(task);

        return ResponseEntity.ok().build();
    }
}
