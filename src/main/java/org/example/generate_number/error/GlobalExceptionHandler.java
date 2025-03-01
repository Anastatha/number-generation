package org.example.generate_number.error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(DatabaseOperationException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                Map.of(
                        "error", "Ошибка базы данных",
                        "message", ex.getMessage(),
                        "status", ex.getStatus().value()
                )
        );
    }
}
