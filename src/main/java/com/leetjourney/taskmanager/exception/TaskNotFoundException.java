package com.leetjourney.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id)
    {
        super("Task Not Found with ID :"+id);
    }
}
