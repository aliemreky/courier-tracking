package com.aliemreky.couriertracking.exception;


import com.aliemreky.couriertracking.model.response.ResponseModel;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionTest {

    @InjectMocks
    private GlobalException globalException;

    @Test
    public void handleSystemException() {
        Exception ex = new Exception("Test Exception");
        ResponseModel<?> response = globalException.handleSystemException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getErrorMessage()).isEqualTo(ex.getMessage());
        assertThat(response.getResponseEntity()).isNull();
    }


    @Test
    public void handleEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
        ResponseModel<?> response = globalException.handleEntityNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getErrorMessage()).isEqualTo(ex.getMessage());
        assertThat(response.getResponseEntity()).isNull();
    }

    @Test
    public void handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Error Message");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseModel<?> response = globalException.handleMethodArgumentNotValidException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getErrorMessage()).isEqualTo("fieldName : Error Message");
        assertThat(response.getResponseEntity()).isNull();

    }

}
