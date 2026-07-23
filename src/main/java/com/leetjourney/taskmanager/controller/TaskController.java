package com.leetjourney.taskmanager.controller;


import com.leetjourney.taskmanager.entity.Task;
import com.leetjourney.taskmanager.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskController {


    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id)
    {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
       Task savedTask = taskRepository.save(task);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task taskToUpdate)
    {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskToUpdate.getTitle());
                    task.setDescription(taskToUpdate.getDescription());
                    Task savedTask = taskRepository.save(task);
                    return ResponseEntity.ok(savedTask);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id)
    {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());

    }


}
