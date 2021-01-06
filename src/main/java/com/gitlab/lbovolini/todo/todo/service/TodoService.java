package com.gitlab.lbovolini.todo.todo.service;

import com.gitlab.lbovolini.todo.common.CrudService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface TodoService extends CrudService<Todo> {

    Todo save(Todo todo, MultipartFile multipartFile);

    Optional<Todo> update(Todo todo, MultipartFile multipartFile);
}
