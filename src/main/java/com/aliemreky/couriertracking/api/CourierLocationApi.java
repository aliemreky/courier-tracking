package com.aliemreky.couriertracking.api;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/courier/{courierId}/location")
public interface CourierLocationApi {

    @PostMapping(value = "/create")
    ResponseModel<CourierLocationLog> create(@PathVariable @NotNull Long courierId, @RequestBody @Valid CreateCourierLocationRequest request) throws Exception;

    @GetMapping("/last-location")
    ResponseModel<CourierLocationLog> getLastCourierlocation(@PathVariable @NotNull Long courierId);

    @GetMapping("/total-distance")
    ResponseModel<Double> getTotalDistance(@PathVariable @NotNull Long courierId);

}
