package org.example.generate_number.controller;

import org.example.generate_number.dto.GenerateSeriesNumberRequest;
import org.example.generate_number.dto.GenerateSeriesNumberResponse;
import org.example.generate_number.service.GenerateNumberService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class GenerateNumberController {
    private final GenerateNumberService generateNumberService;

    public GenerateNumberController(GenerateNumberService generateNumberService) {
        this.generateNumberService = generateNumberService;
    }

    @PostMapping("/generate-series-number")
    public GenerateSeriesNumberResponse generateNumber(@RequestBody GenerateSeriesNumberRequest request) {
        try {
            return generateNumberService.generateNumber(request);
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка при генерации номера", error);
        }
    }

}
