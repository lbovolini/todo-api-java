package com.gitlab.lbovolini.todo.common;

import org.springframework.http.ResponseEntity;

public interface CrudController<T> {

    ResponseEntity<?> delete(String id);

    ResponseEntity<T> findById(String id);

    ResponseEntity<T> save(T t);

    ResponseEntity<?> update(T t);
}
