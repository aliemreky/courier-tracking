package com.aliemreky.couriertracking.exception;

import com.aliemreky.couriertracking.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel<?> handleSystemException(final Exception ex) {
        log.error(ex.getMessage());
        return ResponseModel.warning(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseModel<?> handleEntityNotFoundException(final EntityNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseModel.warning(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());

        BindingResult result = ex.getBindingResult();
        List<String> errorList = new ArrayList<>();
        for (FieldError f : result.getFieldErrors()) {
            errorList.add(f.getField() + " : " + f.getDefaultMessage());
        }

        return ResponseModel.warning(null, errorList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")), HttpStatus.BAD_REQUEST);
    }

}
