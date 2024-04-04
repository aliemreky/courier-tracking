package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import org.springframework.scheduling.annotation.Async;

public interface CourierLocationObserver {

    @Async
    void updateCourierLocation(CourierLocationLog courierLocationLog);
}
