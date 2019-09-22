package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }


    public Optional<Task> getTask(final Long id) {
        return repository.findById(id);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deleteTask(final Task task) {
        repository.delete(task);
    }
}
