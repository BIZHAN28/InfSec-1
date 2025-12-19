package com.bizhanov.secureapi.service;

import com.bizhanov.secureapi.dto.TaskCreateRequest;
import com.bizhanov.secureapi.dto.TaskResponse;
import com.bizhanov.secureapi.dto.TaskUpdateRequest;
import com.bizhanov.secureapi.model.Task;
import com.bizhanov.secureapi.model.User;
import com.bizhanov.secureapi.repository.TaskRepository;
import com.bizhanov.secureapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponse> getAllTasksByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setUserId(user.getId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus("PENDING");
        task.setPriority(request.getPriority() != null ? request.getPriority() : "MEDIUM");
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUserId(id, user.getId());
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        task.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}

