package com.leetjourney.taskmanager.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "Title must not be blank")
        @Size(min=3, max=100,message = "Title Must be between 3 to 100 characters")
        String title,
        @Size(max = 500)
        String description,
        Boolean completed
) {
}
