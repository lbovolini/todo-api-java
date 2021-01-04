package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Atualiza TODOS os campos do usuário.
     * @param user
     * @return Retorna o usuário atualizado depois de salvo na base de dados.
     */
    @Override
    public Optional<User> update(User user) {
        Query query = Query.query(Criteria.where("id").is(user.getId()));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("password", user.getPassword());

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, User.class));
    }
}
