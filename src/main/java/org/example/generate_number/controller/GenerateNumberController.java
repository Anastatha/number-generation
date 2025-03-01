package org.example.generate_number.controller;

import jakarta.validation.Valid;
import org.example.generate_number.dto.GenerateSeriesNumberRequest;
import org.example.generate_number.dto.GenerateSeriesNumberResponse;
import org.example.generate_number.service.GenerateNumberService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenerateNumberController {
    private final GenerateNumberService generateNumberService;

    public GenerateNumberController(GenerateNumberService generateNumberService) {
        this.generateNumberService = generateNumberService;
    }

    @PostMapping("/generate-series-number")
    public GenerateSeriesNumberResponse generateNumber(@Valid @RequestBody GenerateSeriesNumberRequest request) {
        return generateNumberService.generateNumber(request);
    }

}
