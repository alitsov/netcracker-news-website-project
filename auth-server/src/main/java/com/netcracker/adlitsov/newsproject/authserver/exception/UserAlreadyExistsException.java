package com.netcracker.adlitsov.newsproject.authserver.exception;

import com.netcracker.adlitsov.newsproject.authserver.model.User;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(User user) {
        super("User " + user + "already exists!");
    }
}
