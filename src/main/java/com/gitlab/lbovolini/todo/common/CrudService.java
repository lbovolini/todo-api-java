package com.gitlab.lbovolini.todo.common;

import java.util.Optional;

public interface CrudService<T> {

    void delete(String id);

    Optional<T> findById(String id);

    T save(T t);

    Optional<T> update(T t);
}
