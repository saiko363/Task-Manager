package com.leetjourney.taskmanager.service;


import com.leetjourney.taskmanager.dto.TaskRequest;
import com.leetjourney.taskmanager.dto.TaskResponse;
import com.leetjourney.taskmanager.entity.Task;
import com.leetjourney.taskmanager.exception.TaskNotFoundException;
import com.leetjourney.taskmanager.mapper.TaskMapper;
import com.leetjourney.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    //Get All Tasks
    public List<TaskResponse> getAllTasks()
    {
        List<Task> tasks= taskRepository.findAll();
        List<TaskResponse> taskResponses= new ArrayList<>();
        for(Task t: tasks)
        {
            taskResponses.add(taskMapper.toResponse(t));
        }
        return taskResponses;
    }

    public Page<Task> getAllTasks(Pageable pageable)
    {
        return taskRepository.findAll(pageable);
    }


   //Get Tasks by ID
    public TaskResponse getTaskById(Long id)
    {
        Task task= taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));
        return taskMapper.toResponse(task);
    }
   //Create a new Task
    public TaskResponse createTask(TaskRequest task)
    {
        Task entityTask= taskMapper.toEntity(task);
        Task savedTask = taskRepository.save(entityTask);
        return taskMapper.toResponse(savedTask);
    }
   //Update an Existing task
    public TaskResponse updateTask(Long id, TaskRequest updatedTask)
    {
        Task task= taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));

        taskMapper.updateEntityFromRequest(task, updatedTask);

        return taskMapper.toResponse(taskRepository.save(task));

    }
    //Delete Task
    public boolean deleteTask(Long id)
    {
        Task task= taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));

        taskRepository.delete(task);
        return true;

    }

    public List<TaskResponse> getCompletedTasksByStatus(boolean status)
    {
        final List<Task> completedTasks = taskRepository.findByCompleted(status);
        return completedTasks.stream()
                .map(taskMapper:: toResponse)
                .toList();
    }

    public Page<TaskResponse> getCompletedTasksByStatus(boolean status, Pageable pageable)
    {
        final Page<Task> completedTasks = taskRepository.findByCompleted(status, pageable);
        return completedTasks.map(taskMapper:: toResponse);
    }
    public List<Task> searchTaskByTitle(String title)
    {
        return taskRepository.findByTitleContainingIgnoreCase(title);

    }
    public List<Task> findTasksByStatus(boolean status)
    {
        return taskRepository.findTasksByCompletionStatus(status);
    }

    public Page<Task> searchTasksByTitle(String title, Pageable pageable)
    {
        return taskRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Task> findTasksByTitleAndCompletion(String title, Boolean completed, Pageable pageable) {
        return taskRepository.findByTitleContainingAndCompleted(title,completed, pageable);
    }

    public Page<Task> getTasksByCompletion(Boolean completed, Pageable pageable) {
        return taskRepository.findTasksByCompletionStatus(completed,pageable);
    }
}
