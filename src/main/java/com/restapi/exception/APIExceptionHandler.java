package com.restapi.exception;
import com.restapi.domain.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;

/**
 * APIExceptionHandler is used for standardization of the responses
 *
 * @author Duygu Muslu
 * @since  2020-06-17
 * @version 1.0
 */


@Slf4j
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        FieldError fieldError = ex.getBindingResult().getFieldError();
        ResponseDto responseDTO = ResponseDto.builder()
                .status(status.toString())
                .message(fieldError.getDefaultMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ResponseDto responseDTO = ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(Throwable.class)
    public void handle(HttpMessageNotReadableException e) {
        System.out.println("EXCEPTION HAPPENED");
        System.out.println(e);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public final ResponseEntity<Object> handleInvocationTargetException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ResponseDto responseDTO = ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDTO = ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message("Data Integrity is violated, please make sure constraints are unique").build();

        return ResponseEntity.badRequest().body(responseDTO);
    }
}