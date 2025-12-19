package com.bizhanov.secureapi.controller;

import com.bizhanov.secureapi.dto.TaskCreateRequest;
import com.bizhanov.secureapi.dto.TaskResponse;
import com.bizhanov.secureapi.dto.TaskUpdateRequest;
import com.bizhanov.secureapi.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskResponse> tasks = taskService.getAllTasksByUser(username);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        TaskResponse task = taskService.createTask(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        TaskResponse task = taskService.updateTask(id, request, username);
        return ResponseEntity.ok(task);
    }
}

