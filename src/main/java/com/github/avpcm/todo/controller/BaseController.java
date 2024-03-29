package com.github.avpcm.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> onException(Exception ex) {

        return ResponseEntity
                .internalServerError()
                .body(ex.getMessage());
    }
}
