package com.gitlab.lbovolini.todo.common;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    void delete(String id);

    List<T> findAll();

    Optional<T> findById(String id);

    T save(T t);

    Optional<T> update(T t);
}
