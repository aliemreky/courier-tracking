package com.aliemreky.couriertracking.service.impl;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.entity.CourierStoreLog;
import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.repository.CourierStoreLogRepository;
import com.aliemreky.couriertracking.service.CourierLocationObserver;
import com.aliemreky.couriertracking.service.StoreService;
import com.aliemreky.couriertracking.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.aliemreky.couriertracking.util.GeneralConstant.COURIER_TRACKING_DISTANCE;
import static com.aliemreky.couriertracking.util.GeneralConstant.IGNORED_SECOND_OF_COURIER_TRACKING;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierStoreLocationServiceImpl implements CourierLocationObserver {

    private final CourierStoreLogRepository courierStoreLogRepository;
    private final StoreService storeService;

    @Override
    public void updateCourierLocation(CourierLocationLog courierLocationLog) {
        storeService.getAllStore()
                .stream()
                .filter(store -> LocationUtil.getInstance().calculateDistance(courierLocationLog.getLatitude(), courierLocationLog.getLongitude(), store.getLatitude(), store.getLongitude()) <= COURIER_TRACKING_DISTANCE)
                .forEach(store -> create(store, courierLocationLog, new Date()));

        log.info("Courier location updated for courier: {}", courierLocationLog.getCourier().getId());
    }

    private void create(Store store, CourierLocationLog courierLocationLog, Date trackingDate) {
        Optional<CourierStoreLog> courierTrackingOptional = courierStoreLogRepository.findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier());
        if (courierTrackingOptional.isPresent()) {
            CourierStoreLog courierTracking = courierTrackingOptional.get();
            if (IGNORED_SECOND_OF_COURIER_TRACKING >= getDiffSeconds(courierTracking.getTrackingDate(), trackingDate)) {
                log.info("Courier location has been ignored for courier: {} and store: {}", courierLocationLog.getCourier().getId(), store.getId());
                return;
            }
        }

        CourierStoreLog courierStoreLog = new CourierStoreLog();
        courierStoreLog.setCourier(courierLocationLog.getCourier());
        courierStoreLog.setStore(store);
        courierStoreLog.setTrackingDate(trackingDate);

        courierStoreLogRepository.save(courierStoreLog);

        log.info("Courier location has been created for courier: {} and store: {}", courierLocationLog.getCourier().getId(), store.getId());
    }

    public long getDiffSeconds(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toSeconds((endDate.getTime() - startDate.getTime()));
    }
}
