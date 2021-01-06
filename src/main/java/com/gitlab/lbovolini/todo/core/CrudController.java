package com.gitlab.lbovolini.todo.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<T> {

    ResponseEntity<?> delete(String id);

    ResponseEntity<T> findById(String id);

    ResponseEntity<List<T>> findAll();

    ResponseEntity<T> save(T t);

    ResponseEntity<?> update(T t);
}
