package com.github.avpcm.todo.service;

import java.util.Optional;

import com.github.avpcm.todo.model.User;

public interface UserService {

    User register(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
