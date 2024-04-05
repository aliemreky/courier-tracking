package com.aliemreky.couriertracking.api;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/courier")
public interface CourierApi {

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseModel<Courier> create(@RequestBody @Valid CreateCourierRequest request);

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseModel<Boolean> delete(@PathVariable @NotNull Long id);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseModel<Courier> getCourierById(@PathVariable @NotNull Long id);

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    ResponseModel<List<Courier>> getAllCourier();
}
