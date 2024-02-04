package com.github.avpcm.todo.service;

import java.util.Optional;

import com.github.avpcm.todo.model.User;

public interface AuthService {

    String login(User user);

    Optional<User> getByToken(String token);

    void logout(String token);
}
