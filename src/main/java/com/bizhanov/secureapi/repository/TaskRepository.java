package com.bizhanov.secureapi.repository;

import com.bizhanov.secureapi.model.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    
    @Query("SELECT * FROM tasks WHERE user_id = :userId ORDER BY created_at DESC")
    List<Task> findAllByUserId(@Param("userId") Long userId);
    
    @Query("SELECT * FROM tasks WHERE id = :id AND user_id = :userId")
    Task findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}

