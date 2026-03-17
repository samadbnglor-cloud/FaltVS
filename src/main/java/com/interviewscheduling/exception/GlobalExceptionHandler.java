package com.interviewscheduling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleEnumParseError(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("Cannot deserialize value of type")) {
            // Try to extract the field, value, and allowed values
            String field = null, value = null, allowed = null;
            int valueStart = message.indexOf("from String \"");
            int valueEnd = message.indexOf("\"", valueStart + 13);
            if (valueStart != -1 && valueEnd != -1) {
                value = message.substring(valueStart + 13, valueEnd);
            }
            int typeStart = message.indexOf("type `");
            int typeEnd = message.indexOf("`", typeStart + 6);
            if (typeStart != -1 && typeEnd != -1) {
                field = message.substring(typeStart + 6, typeEnd);
            }
            int allowedStart = message.indexOf("Enum class: [");
            int allowedEnd = message.indexOf("]", allowedStart);
            if (allowedStart != -1 && allowedEnd != -1) {
                allowed = message.substring(allowedStart + 13, allowedEnd);
            }
            if (allowed != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Allowed status values: " + allowed);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid enum value");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request payload: " + ex.getMessage());
    }
}