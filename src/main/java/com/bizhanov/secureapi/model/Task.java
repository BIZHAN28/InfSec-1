package com.bizhanov.secureapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {
    @Id
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String createdAt;
    private String updatedAt;
}

