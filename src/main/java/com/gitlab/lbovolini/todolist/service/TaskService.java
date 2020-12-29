package com.gitlab.lbovolini.todolist.service;

import com.gitlab.lbovolini.todolist.model.Task;
import com.gitlab.lbovolini.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements CrudService<Task> {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void delete(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void update(Task task) {
        taskRepository.save(task);
    }
}
