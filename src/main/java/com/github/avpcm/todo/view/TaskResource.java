package com.github.avpcm.todo.view;

import java.util.List;
import java.util.stream.Collectors;

import com.github.avpcm.todo.model.Task;

public record TaskResource(Long id, String title, String description) {

    public static TaskResource of(Task task) {
        return new TaskResource(task.getId(), task.getTitle(), task.getDescription());
    }

    public static List<TaskResource> ofList(List<Task> tasks) {
        return tasks
                .stream()
                .map(TaskResource::of)
                .collect(Collectors.toList());
    }
}
