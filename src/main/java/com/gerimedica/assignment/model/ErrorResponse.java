package com.gerimedica.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Prashanth Nander
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
