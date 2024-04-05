package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;

import java.util.List;
import java.util.Optional;

public interface CourierService {

    Courier createCourier(CreateCourierRequest request);

    Boolean deleteCourier(Long id);

    Optional<Courier> getCourierById(Long id);

    List<Courier> getAllCourier();
}
