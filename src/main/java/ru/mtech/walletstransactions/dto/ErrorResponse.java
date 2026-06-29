package ru.mtech.walletstransactions.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private LocalDateTime time;

    public ErrorResponse(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
