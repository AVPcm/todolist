package com.github.avpcm.todo.controller;

import java.util.List;
import java.util.Optional;

import com.github.avpcm.todo.Constants;
import com.github.avpcm.todo.model.Task;
import com.github.avpcm.todo.model.User;
import com.github.avpcm.todo.service.TaskService;
import com.github.avpcm.todo.view.TaskContentResource;
import com.github.avpcm.todo.view.TaskResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController extends BaseController {

    @Autowired
    TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResource>> getAllByUserId(
            @RequestHeader(Constants.TOKEN_HEADER_NAME) String token,
            @RequestAttribute(Constants.USER_ATTRIBUTE_NAME) User user) {
        List<Task> tasks = service.getAllByUser(user.getId());

        return ResponseEntity
                .ok(TaskResource.ofList(tasks));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestHeader(Constants.TOKEN_HEADER_NAME) String token,
                                       @RequestAttribute(Constants.USER_ATTRIBUTE_NAME) User user,
                                       @RequestBody TaskContentResource content) {
        Task task = service.create(content.toModel(user, null));

        return ResponseEntity
                .ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@RequestHeader(Constants.TOKEN_HEADER_NAME) String token,
                                       @RequestAttribute(Constants.USER_ATTRIBUTE_NAME) User user,
                                       @PathVariable Long id,
                                       @RequestBody TaskContentResource content) {
        Optional<Task> task = service.update(user.getId(), content.toModel(user, id));

        return task.isEmpty()
               ? ResponseEntity.notFound().build()
               : ResponseEntity.ok(task.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader(Constants.TOKEN_HEADER_NAME) String token,
                                       @RequestAttribute(Constants.USER_ATTRIBUTE_NAME) User user,
                                       @PathVariable Long id) {
        service.deleteById(user.getId(), id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
