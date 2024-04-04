package com.aliemreky.couriertracking.controller;

import com.aliemreky.couriertracking.api.CourierApi;
import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import com.aliemreky.couriertracking.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CourierController implements CourierApi {

    private final CourierService courierService;

    @Override
    public ResponseModel<Courier> create(CreateCourierRequest request) {
        return ResponseModel.success(courierService.createCourier(request));
    }

    @Override
    public ResponseModel<Boolean> delete(Long id) {
        return ResponseModel.success(courierService.deleteCourier(id));
    }

    @Override
    public ResponseModel<Courier> getCourierById(Long id) {
        return ResponseModel.success(courierService.getCourierById(id));
    }

    @Override
    public ResponseModel<List<Courier>> getAllCourier() {
        return ResponseModel.success(courierService.getAllCourier());
    }
}
