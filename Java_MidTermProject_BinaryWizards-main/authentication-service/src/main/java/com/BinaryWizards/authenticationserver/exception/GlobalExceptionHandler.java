package com.BinaryWizards.authenticationserver.exception;

import com.BinaryWizards.authenticationserver.model.LoginResponse;
import com.BinaryWizards.authenticationserver.model.RegistrationResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<?> UserNotFoundByEmailExceptionHandler(UserNotFoundByEmailException e){
        return new ResponseEntity<>(LoginResponse.builder()
                .success(false)
                .message(List.of(e.getMessage()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String uri=request.getRequestURI();
        return switch (uri) {
            case "/auth-server/register" -> new ResponseEntity<>(RegistrationResponse.builder()
                    .success(false)
                    .message(errors)
                    .build(), HttpStatus.BAD_REQUEST);
            case "/auth-server/login" -> new ResponseEntity<>(LoginResponse.builder()
                    .success(false)
                    .message(errors)
                    .build(), HttpStatus.BAD_REQUEST);
            default -> ResponseEntity.internalServerError().build();
        };
    }

}
