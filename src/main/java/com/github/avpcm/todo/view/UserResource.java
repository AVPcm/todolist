package com.github.avpcm.todo.view;

import java.util.List;
import java.util.stream.Collectors;

import com.github.avpcm.todo.model.User;

public record UserResource(String login, String password) {

    public static UserResource of(User user) {
        return new UserResource(user.getLogin(), user.getPassword());
    }

    public static List<UserResource> ofList(List<User> users) {
        return users
                .stream()
                .map(UserResource::of)
                .collect(Collectors.toList());
    }

    public User toModel() {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }
}
