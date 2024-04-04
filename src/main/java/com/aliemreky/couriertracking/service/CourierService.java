package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;

import java.util.List;

public interface CourierService {

    Courier createCourier(CreateCourierRequest request);

    Boolean deleteCourier(Long id);

    Courier getCourierById(Long id);

    List<Courier> getAllCourier();
}
