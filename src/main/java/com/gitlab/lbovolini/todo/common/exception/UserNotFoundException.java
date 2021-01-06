package com.gitlab.lbovolini.todo.common.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String message = "User not found";

    public UserNotFoundException() {
        super(message);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
