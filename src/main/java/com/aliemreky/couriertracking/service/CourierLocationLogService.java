package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;

import java.util.Optional;

public interface CourierLocationLogService {

    CourierLocationLog create(Long courierId, CreateCourierLocationRequest request) throws Exception;

    Optional<CourierLocationLog> findLastByCourier(Long courierId);

    double getTotalTravelDistance(Long courierId);
}
