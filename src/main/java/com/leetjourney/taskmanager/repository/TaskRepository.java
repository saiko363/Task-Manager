package com.leetjourney.taskmanager.repository;

import com.leetjourney.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    @Query("select t from Task t where t.completed= :status")
    List<Task> findTasksByCompletionStatus(@Param("status") boolean status);

}
