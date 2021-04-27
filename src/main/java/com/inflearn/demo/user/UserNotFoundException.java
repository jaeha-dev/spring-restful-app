package com.inflearn.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2xx -> Ok
// 4xx -> Client Error
// 5xx -> Server Error
@ResponseStatus(HttpStatus.NOT_FOUND) // 404
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}