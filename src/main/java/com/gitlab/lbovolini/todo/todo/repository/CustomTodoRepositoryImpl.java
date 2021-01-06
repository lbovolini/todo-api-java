package com.gitlab.lbovolini.todo.todo.repository;

import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTodoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Todo> update(Todo todo) {
        Query query = Query.query(Criteria.where("id").is(todo.getId()));

        Update update = new Update();
        update.set("userId", todo.getUserId());
        update.set("tasks", todo.getTasks());
        update.set("attachments", todo.getAttachments());

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, Todo.class));
    }
}
