package com.leetjourney.taskmanager.controller;


import com.leetjourney.taskmanager.dto.TaskRequest;
import com.leetjourney.taskmanager.dto.TaskResponse;
import com.leetjourney.taskmanager.entity.Task;
import com.leetjourney.taskmanager.repository.TaskRepository;
import com.leetjourney.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id)
    {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task){
       TaskResponse savedTask = taskService.createTask(task);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,@Valid @RequestBody TaskRequest taskToUpdate)
    {
        return taskService.updateTask(id, taskToUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id)
    {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletionStatus(@PathVariable boolean status)
    {
        return taskService.getCompletedTasksByStatus(status);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title)
    {
        return taskService.searchTaskByTitle(title);
    }

    @GetMapping("/completed/status")
    public List<Task> findTasksWithStatus(@RequestParam boolean status)
    {
        return taskService.findTasksByStatus(status);
    }



}
