package com.bizhanov.secureapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM, or HIGH")
    private String priority = "MEDIUM";
}

