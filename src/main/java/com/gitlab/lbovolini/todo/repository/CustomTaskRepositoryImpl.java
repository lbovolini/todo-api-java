package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTaskRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Atualiza TODOS os campos da tarefa.
     * @param task
     * @return Retorna a tarefa atualizada depois de salva na base de dados.
     */
    @Override
    public Optional<Task> update(Task task) {
        Query query = Query.query(Criteria.where("id").is(task.getId()));

        Update update = new Update();
        update.set("name", task.getName());
        update.set("description", task.getDescription());
        update.set("date", task.getDate());
        update.set("attachment", task.getAttachment());

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, Task.class));
    }
}
