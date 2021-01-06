package com.gitlab.lbovolini.todo.user.repository;

import com.gitlab.lbovolini.todo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final Set<String> collectionsNameWhereUserIsPresent = Set.of("todo");

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Remove o usuário e todas as coleções associadas ao usuário.
     * @param id
     */
    @Override
    @Transactional
    public void delete(String id) {
        Query removeCollectionWhereUserIsPresentQuery = Query.query(Criteria.where("{ user: id }").is(id));
        Query removeUserQuery = Query.query(Criteria.where("id").is(id));

        collectionsNameWhereUserIsPresent.forEach(collectionName -> {
            mongoTemplate.remove(removeCollectionWhereUserIsPresentQuery, collectionName);
        });

        User user = mongoTemplate.findAndRemove(removeUserQuery, User.class);

        if (Objects.isNull(user)) {
            throw new RuntimeException("User not found"); //UserNotFoundException();
        }
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
