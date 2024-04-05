package com.aliemreky.couriertracking.api;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/courier/{courierId}/location")
public interface CourierLocationApi {

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseModel<CourierLocationLog> create(@PathVariable @NotNull Long courierId, @RequestBody @Valid CreateCourierLocationRequest request) throws Exception;

    @GetMapping(value = "/last-location", produces = APPLICATION_JSON_VALUE)
    ResponseModel<CourierLocationLog> getLastCourierLocation(@PathVariable @NotNull Long courierId);

    @GetMapping(value = "/total-distance", produces = APPLICATION_JSON_VALUE)
    ResponseModel<Double> getTotalDistance(@PathVariable @NotNull Long courierId);

}
