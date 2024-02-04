package com.github.avpcm.todo.repository;

import java.util.List;

import com.github.avpcm.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUserId(Long userId);
}
