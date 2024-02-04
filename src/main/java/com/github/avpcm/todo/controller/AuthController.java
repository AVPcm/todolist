package com.github.avpcm.todo.controller;

import com.github.avpcm.todo.Constants;
import com.github.avpcm.todo.model.User;
import com.github.avpcm.todo.service.AuthService;
import com.github.avpcm.todo.service.UserService;
import com.github.avpcm.todo.view.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService service;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserResource user) {
        String token = service.login(user.toModel());

        return ResponseEntity
                .ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(Constants.TOKEN_HEADER_NAME) String token) {
        service.logout(token);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResource> register(@RequestBody UserResource data) {
        User user = userService.register(data.toModel());

        return ResponseEntity
                .ok(UserResource.of(user));
    }
}
