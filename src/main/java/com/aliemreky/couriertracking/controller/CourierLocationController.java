package com.aliemreky.couriertracking.controller;

import com.aliemreky.couriertracking.api.CourierLocationApi;
import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import com.aliemreky.couriertracking.service.CourierLocationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CourierLocationController implements CourierLocationApi {

    private final CourierLocationLogService courierLocationLogService;

    @Override
    public ResponseModel<CourierLocationLog> create(Long courierId, CreateCourierLocationRequest request) throws Exception {
        return ResponseModel.success(courierLocationLogService.create(courierId, request));
    }

    @Override
    public ResponseModel<CourierLocationLog> getLastCourierLocation(Long courierId) {
        Optional<CourierLocationLog> locationLog = courierLocationLogService.findLastByCourier(courierId);

        if(locationLog.isPresent())
            return ResponseModel.success(locationLog.get());

        return ResponseModel.warning(null, "The Location " + courierId + " not found for courier " + courierId, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseModel<Double> getTotalDistance(Long courierId) {
        return ResponseModel.success(courierLocationLogService.getTotalTravelDistance(courierId));
    }
}
