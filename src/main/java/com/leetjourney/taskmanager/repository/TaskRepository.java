package com.leetjourney.taskmanager.repository;

import com.leetjourney.taskmanager.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    @Query("select t from Task t where t.completed= :status")
    List<Task> findTasksByCompletionStatus(@Param("status") boolean status);

    //pagination methods

    Page<Task> findByCompleted(boolean completed, Pageable pageable);
    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select t from Task t where t.completed= :status")
    Page<Task> findTasksByCompletionStatus(@Param("status") boolean status, Pageable pageable);

    @Query("select t" +
            " from Task t " +
            "where LOWER(t.title) LIKE LOWER(CONCAT('%', :title,'%')) AND t.completed = :completed")
    Page<Task> findByTitleContainingAndCompleted(String title, boolean Completed,Pageable pageable);
}
