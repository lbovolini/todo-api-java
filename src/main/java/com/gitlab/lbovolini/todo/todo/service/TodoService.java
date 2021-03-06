package com.gitlab.lbovolini.todo.todo.service;

import com.gitlab.lbovolini.todo.common.CrudService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TodoService extends CrudService<Todo> {

    void delete(String id, String loggedUserId);

    Optional<List<Resource>> downloadAttachments(String id);

    Todo save(Todo todo, MultipartFile multipartFile);

    Optional<Todo> update(Todo todo, MultipartFile multipartFile);
}
