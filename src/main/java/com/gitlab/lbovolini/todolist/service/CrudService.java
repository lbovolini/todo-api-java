package com.gitlab.lbovolini.todolist.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    void delete(String id);

    List<T> findAll();

    Optional<T> findById(String id);

    T save(T task);

    void update(T task);
}
