package com.BinaryWizards.apigateway.exception;

import com.BinaryWizards.apigateway.model.GenericError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException e) {
        return new ResponseEntity<>(GenericError.builder()
                .message("Jwt expired")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> malformedJwtExceptionHandler(MalformedJwtException e) {
        return new ResponseEntity<>(GenericError.builder()
                .message("JWT is not properly formed or contains invalid JSON or base64 encoding")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> signatureExceptionHandler(SignatureException e) {
        return new ResponseEntity<>(GenericError.builder()
                .message("JWT has been tampered with or corrupted")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?> jwtExpiredHandler(UnsupportedJwtException e) {
        return new ResponseEntity<>(GenericError.builder()
                .message("JWT uses an unsupported feature or algorithm")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(GenericError.builder()
                .message("unsupported request")
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
