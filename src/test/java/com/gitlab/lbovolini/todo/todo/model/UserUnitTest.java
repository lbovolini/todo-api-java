package com.gitlab.lbovolini.todo.model;

import com.gitlab.lbovolini.todo.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUnitTest {

    private final String id = "5fecae92fc4878605c5c7a5b";
    private final String username = "lucas";
    private final String password = "pass11word";

    @Test
    void shouldCreateUser() {
        User user = new User(id, username, password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void shouldSetUserAttributes() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }
}