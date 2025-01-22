package org.example.devnet.api_v1.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.devnet.api_v1.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.BindException;

@ControllerAdvice
public class ControllerExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(HttpServletRequest request,
                                                            Exception e) {

        HttpStatus defaultStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getRequestURI());
        errorResponseDto.setMethod(request.getMethod());

        if (e instanceof BindException || e instanceof MethodArgumentNotValidException || e instanceof IllegalArgumentException
                || e instanceof HandlerMethodValidationException) {
            defaultStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof EntityNotFoundException) {
            defaultStatus = HttpStatus.NOT_FOUND;
        }

        errorResponseDto.setStatus(defaultStatus.value());
        errorResponseDto.setError(defaultStatus.getReasonPhrase());

        return ResponseEntity.status(defaultStatus).body(errorResponseDto);
    }
}
