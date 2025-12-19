package com.bizhanov.secureapi.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TaskUpdateRequest {
    private String title;
    private String description;
    
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", message = "Status must be PENDING, IN_PROGRESS, or COMPLETED")
    private String status;
    
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM, or HIGH")
    private String priority;
}

