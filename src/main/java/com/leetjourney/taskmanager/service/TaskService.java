package com.leetjourney.taskmanager.service;


import com.leetjourney.taskmanager.dto.TaskRequest;
import com.leetjourney.taskmanager.dto.TaskResponse;
import com.leetjourney.taskmanager.entity.Task;
import com.leetjourney.taskmanager.exception.TaskNotFoundException;
import com.leetjourney.taskmanager.mapper.TaskMapper;
import com.leetjourney.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
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

    public List<Task> getCompletedTasksByStatus(boolean status)
    {
        return taskRepository.findByCompleted(status);
    }
    public List<Task> searchTaskByTitle(String title)
    {
        return taskRepository.findByTitleContainingIgnoreCase(title);

    }
    public List<Task> findTasksByStatus(boolean status)
    {
        return taskRepository.findTasksByCompletionStatus(status);
    }
}
