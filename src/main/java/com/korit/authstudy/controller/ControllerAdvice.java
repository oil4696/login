package com.korit.authstudy.controller;

import com.korit.authstudy.exception.BearerValidException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(AuthenticationException.class )
    public ResponseEntity<?> unauthorized(AuthenticationException exception) {      // 서비스쪽에서 오류가 터지면 요청은 받지않고 예외를 터트린다
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
    @ExceptionHandler(BearerValidException.class)
    public ResponseEntity<?> isNotBearer(BearerValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<?> jwtError(JwtException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
