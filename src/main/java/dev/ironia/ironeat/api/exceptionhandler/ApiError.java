package dev.ironia.ironeat.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {

    private LocalDateTime dateTime;
    private String message;
}