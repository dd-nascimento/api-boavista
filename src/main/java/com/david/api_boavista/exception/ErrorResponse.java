package com.david.api_boavista.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class ErrorResponse {
    
    @JsonFormat(pattern = "dd-MM-yyyy - HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> fields;
}
