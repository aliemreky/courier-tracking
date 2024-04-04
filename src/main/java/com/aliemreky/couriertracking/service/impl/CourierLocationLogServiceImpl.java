package com.aliemreky.couriertracking.service.impl;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;
import com.aliemreky.couriertracking.repository.CourierLocationLogRepository;
import com.aliemreky.couriertracking.repository.CourierRepository;
import com.aliemreky.couriertracking.service.CourierLocationLogService;
import com.aliemreky.couriertracking.service.CourierLocationObserver;
import com.aliemreky.couriertracking.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourierLocationLogServiceImpl implements CourierLocationLogService {

    private final List<CourierLocationObserver> observerCourierLocation;
    private final CourierLocationLogRepository courierLocationLogRepository;
    private final CourierRepository courierRepository;

    @Override
    public CourierLocationLog create(Long courierId, CreateCourierLocationRequest request) throws Exception {
        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new Exception("The Courier " + courierId + " not found!"));

        CourierLocationLog courierLocationLog = new CourierLocationLog();
        courierLocationLog.setCourier(courier);
        courierLocationLog.setLatitude(request.getLatitude());
        courierLocationLog.setLongitude(request.getLongitude());
        courierLocationLog.setTravelDistance(calculateTravelDistance(courier.getId(), request.getLatitude(), request.getLongitude()));

        CourierLocationLog savedCourierLocationLog = courierLocationLogRepository.save(courierLocationLog);
        notifyObserver(savedCourierLocationLog);

        log.info("Courier geolocation created successfully. CourierId: {}, CourierGeolocationId: {}", courierId, savedCourierLocationLog.getId());

        return savedCourierLocationLog;
    }

    @Override
    public Optional<CourierLocationLog> findLastByCourier(Long courierId) {
        List<CourierLocationLog> courierLocationLogs = courierLocationLogRepository.findTopByCourierOrderByIdDesc(courierId);
        return courierLocationLogs.stream().findFirst();
    }

    @Override
    public double getTotalTravelDistance(Long courierId) {
        return courierLocationLogRepository.findByCourierId(courierId).stream()
                .mapToDouble(CourierLocationLog::getTravelDistance)
                .sum();
    }

    private void notifyObserver(CourierLocationLog courierLocationLog) {
        observerCourierLocation.forEach(observer -> observer.updateCourierLocation(courierLocationLog));
    }

    private double calculateTravelDistance(Long courierId, double lat, double lng) {
        return findLastByCourier(courierId).stream()
                .mapToDouble(courierGeolocation -> LocationUtil.getInstance().calculateDistance(courierGeolocation.getLatitude(), courierGeolocation.getLongitude(), lat, lng))
                .findFirst()
                .orElse(0.0);
    }


}
