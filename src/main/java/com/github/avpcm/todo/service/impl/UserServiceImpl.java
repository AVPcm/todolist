package com.github.avpcm.todo.service.impl;

import java.util.Optional;

import com.github.avpcm.todo.model.User;
import com.github.avpcm.todo.repository.UserRepository;
import com.github.avpcm.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User register(User user) {
        User copy = new User();
        copy.setLogin(user.getLogin());
        copy.setPassword(user.getPassword());

        return repository.save(copy);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }
}
