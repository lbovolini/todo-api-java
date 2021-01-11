package com.gitlab.lbovolini.todo.todo.repository;

import com.gitlab.lbovolini.todo.common.exception.ForbiddenException;
import com.gitlab.lbovolini.todo.common.exception.NotFoundException;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Objects;
import java.util.Optional;

public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTodoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void delete(String id, String loggedUserId) {
        Query findTodoQuery = Query.query(Criteria.where("id").is(id));
        Todo todo = mongoTemplate.findOne(findTodoQuery, Todo.class);

        if (Objects.isNull(todo)) {
            throw new NotFoundException("Todo not found");
        }

        if (!todo.getUserId().equals(loggedUserId)) {
            throw new ForbiddenException("You cannot delete another user");
        }

        Query removeUserQuery = Query.query(Criteria.where("id").is(id));
        mongoTemplate.remove(removeUserQuery, Todo.class);
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
