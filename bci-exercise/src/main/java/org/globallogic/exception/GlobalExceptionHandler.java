package org.globallogic.exception;

import org.globallogic.dto.ErrorDto;
import org.globallogic.dto.ExceptionResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception) {
        return this.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception,
                "Something went wrong. Please try again.");
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException exception,
                                                                 WebRequest request) {
        return this.getResponseEntity(HttpStatus.BAD_REQUEST.value(), exception);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<Object> handleTokenNotValidException(TokenNotValidException exception,
                                                                  WebRequest request) {
        return this.getResponseEntity(HttpStatus.BAD_REQUEST.value(), exception);
    }

    @ExceptionHandler(NotValidCredentialsException.class)
    public ResponseEntity<Object> handleTokenNotValidException(NotValidCredentialsException exception,
                                                               WebRequest request) {
        return this.getResponseEntity(HttpStatus.BAD_REQUEST.value(), exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorDto> errors = new ArrayList<>();
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            ErrorDto error = ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value()).detail(objectError.getDefaultMessage())
                    .timestamp(Timestamp.from(Instant.now())).build();
            errors.add(error);
        }

        return new ResponseEntity<>(new ExceptionResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(int httpStatus, Exception exception, String message) {
        ErrorDto error = ErrorDto.builder().code(httpStatus).detail(message == null ? exception.getMessage() : message)
                .timestamp(Timestamp.from(Instant.now())).build();
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(error);
        return new ResponseEntity<>(new ExceptionResponseDto(errors), HttpStatus.valueOf(error.getCode()));
    }

    private ResponseEntity<Object> getResponseEntity(int httpStatus, Exception exception) {
        return this.getResponseEntity(httpStatus, exception, null);
    }
}
