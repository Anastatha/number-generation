package org.example.generate_number.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GenerateSeriesNumberRequest(

        @NotBlank(message = "Код организации не должен быть пустым")
        String orgCode,

        @NotBlank(message = "Год не должен быть пустым")
        @Size(max = 4, message = "Год должен содержать максимум 4 символа")
        @Pattern(regexp = "\\d{1,4}", message = "Год должен содержать только цифры")
        String year
) {}
