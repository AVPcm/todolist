package com.github.avpcm.todo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.avpcm.todo.model.User;
import com.github.avpcm.todo.service.AuthService;
import com.github.avpcm.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService service;

    private final ConcurrentHashMap<String, User> activeSessions = new ConcurrentHashMap<>();

    @Override
    public String login(User user) {
        User stored = service.findByLoginAndPassword(user.getLogin(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("no such login/password combinations found"));

        String token = generateToken(stored);
        activeSessions.put(token, stored);
        return token;
    }

    public Optional<User> getByToken(String token) {
        return Optional.ofNullable(activeSessions.get(token));
    }

    @Override
    public void logout(String token) {
        activeSessions.remove(token);
    }

    private String generateToken(User user) {
        String raw = user.getLogin() + "-" + user.getPassword();
        return Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }
}
