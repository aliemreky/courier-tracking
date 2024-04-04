package com.aliemreky.couriertracking.model;

import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResponseModelTest {

    @Test
    public void testSuccessResponse() {
        Store store = new Store();
        store.setName("migros one");
        ResponseModel<Store> result = ResponseModel.success(store);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getResponseEntity()).isNotNull();
        assertThat(result.getResponseEntity().getName()).isEqualTo(store.getName());
        assertThat(result.getErrors()).isNull();
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    public void testSuccessResponse_version2() {
        Store store = new Store();
        store.setName("migros one");
        ResponseModel<Store> result = ResponseModel.success(store,HttpStatus.OK);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getResponseEntity()).isNotNull();
        assertThat(result.getResponseEntity().getName()).isEqualTo(store.getName());
        assertThat(result.getErrors()).isNull();
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    public void testWarningResponse() {
        ResponseModel<Store> result = ResponseModel.warning(null, "Warning message", HttpStatus.BAD_REQUEST);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getResponseEntity()).isNull();
        assertThat(result.getErrors()).isNull();
        assertThat(result.getErrorMessage()).isEqualTo("Warning message");
    }
}
