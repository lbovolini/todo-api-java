package com.gitlab.lbovolini.todo.authorization;

import com.gitlab.lbovolini.todo.common.model.User;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    public boolean isOwnerById(String id) {
        User loggedUser = (User)authentication.getPrincipal();

        return loggedUser.getId().equals(id);
    }

    public boolean isOwnerByUsername(String username) {
        User loggedUser = (User)authentication.getPrincipal();

        return loggedUser.getUsername().equals(username);
    }

    public boolean isOwner(User user) {
        User loggedUser = (User)authentication.getPrincipal();

        return loggedUser.getId().equals(user.getId());
    }

    public boolean isOwner(Optional<User> userOptional) {
        User loggedUser = (User)authentication.getPrincipal();

        return userOptional.filter(user -> user.getId().equals(loggedUser.getId()))
                .isPresent();
    }
}
