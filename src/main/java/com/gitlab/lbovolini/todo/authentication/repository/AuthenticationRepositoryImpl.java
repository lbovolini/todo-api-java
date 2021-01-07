package com.gitlab.lbovolini.todo.authentication.repository;

import com.gitlab.lbovolini.todo.common.DefaultUser;
import com.gitlab.lbovolini.todo.common.exception.NotFoundException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AuthenticationRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<DefaultUser> findByUsername(String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        DefaultUser defaultUser = mongoTemplate.findOne(query, DefaultUser.class, "user");

        return Optional.ofNullable(defaultUser);
    }

    @Override
    public void lockAccount(String username) {
        Query query = Query.query(Criteria.where("username").is(username));

        Update update = new Update();
        update.set("accountNonLocked", false);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "user");

        if (updateResult.getMatchedCount() < 1) {
            throw new NotFoundException("Username not found");
        }
    }

    @Override
    public void unlockAccount(String username) {
        Query query = Query.query(Criteria.where("username").is(username));

        Update update = new Update();
        update.set("accountNonLocked", true);

        mongoTemplate.updateFirst(query, update, "user");
    }
}
