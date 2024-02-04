package com.github.avpcm.todo.service;

import java.util.List;
import java.util.Optional;

import com.github.avpcm.todo.model.Task;

public interface TaskService {

    Task create(Task task);

    List<Task> getAllByUser(Long userId);

    Optional<Task> update(Long userId, Task task);

    void deleteById(Long userId, Long id);
}
