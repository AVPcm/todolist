package com.github.avpcm.todo.service.impl;

import java.util.List;
import java.util.Optional;

import com.github.avpcm.todo.model.Task;
import com.github.avpcm.todo.repository.TaskRepository;
import com.github.avpcm.todo.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NonNull
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public Task create(Task task) {
        Task copy = new Task();
        copy.setTitle(task.getTitle());
        copy.setDescription(task.getDescription());
        copy.setUser(task.getUser());

        return repository.save(copy);
    }

    @Override
    public List<Task> getAllByUser(Long userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public Optional<Task> update(Long userId, Task task) {
        Task current = repository.findById(task.getId())
                .orElse(null);

        return current == null
               ? Optional.empty()
               : Optional.of(repository.save(task));
    }

    @Override
    public void deleteById(Long userId, Long id) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            return;
        }

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("user can delete only his own tasks");
        }

        repository.deleteById(id);
    }
}
