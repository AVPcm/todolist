package com.github.avpcm.todo.view;

import com.github.avpcm.todo.model.Task;
import com.github.avpcm.todo.model.User;

public record TaskContentResource(String title, String description) {

    public static TaskContentResource of(Task task) {
        return new TaskContentResource(task.getTitle(), task.getDescription());
    }

    public Task toModel(User user, Long id) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setUser(user);

        return task;
    }
}
