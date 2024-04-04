package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.entity.CourierStoreLog;
import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.repository.CourierStoreLogRepository;
import com.aliemreky.couriertracking.service.impl.CourierStoreLocationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierStoreLocationServiceTest {

    @InjectMocks
    private CourierStoreLocationServiceImpl courierStoreLocationService;

    @Mock
    private CourierStoreLogRepository courierStoreLogRepository;

    @Mock
    private StoreService storeService;

    @Test
    public void update_shouldSaveCourierTracking_whenCourierAroundStores() {
        CourierLocationLog courierLocationLog = createCourierLocationLog();
        Store store = createStore();
        when(storeService.getAllStore()).thenReturn(Collections.singletonList(store));
        when(courierStoreLogRepository.findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier())).thenReturn(Optional.empty());

        courierStoreLocationService.updateCourierLocation(courierLocationLog);

        InOrder inOrder = Mockito.inOrder(storeService, courierStoreLogRepository, courierStoreLogRepository);
        inOrder.verify(storeService).getAllStore();
        inOrder.verify(courierStoreLogRepository).findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier());
        inOrder.verify(courierStoreLogRepository).save(any(CourierStoreLog.class));
    }

    @Test
    public void update_shouldNotSaveCourierTracking_whenCourierAroundStoresAndHaveRecordInLastMinutes() {
        CourierLocationLog courierLocationLog = createCourierLocationLog();
        Store store = createStore();
        CourierStoreLog courierStoreLog = new CourierStoreLog();
        courierStoreLog.setStore(store);
        courierStoreLog.setCourier(courierLocationLog.getCourier());
        courierStoreLog.setTrackingDate(new Date(System.currentTimeMillis() - 1000 * 60));
        when(storeService.getAllStore()).thenReturn(Collections.singletonList(store));
        when(courierStoreLogRepository.findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier())).thenReturn(Optional.of(courierStoreLog));

        courierStoreLocationService.updateCourierLocation(courierLocationLog);

        InOrder inOrder = Mockito.inOrder(storeService, courierStoreLogRepository, courierStoreLogRepository);
        inOrder.verify(storeService).getAllStore();
        inOrder.verify(courierStoreLogRepository).findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier());
        inOrder.verify(courierStoreLogRepository, times(0)).save(any(CourierStoreLog.class));
    }

    @Test
    public void update_shouldSaveCourierTracking_whenCourierAroundStoresAndNoRecordInLastMinutes() {
        CourierLocationLog courierLocationLog = createCourierLocationLog();
        Store store = createStore();
        CourierStoreLog courierStoreLog = new CourierStoreLog();
        courierStoreLog.setStore(store);
        courierStoreLog.setCourier(courierLocationLog.getCourier());
        courierStoreLog.setTrackingDate(new Date(System.currentTimeMillis() - 1000 * 61));
        courierStoreLog.setCreateDate(new Date(System.currentTimeMillis() - 1000 * 61));
        when(storeService.getAllStore()).thenReturn(Collections.singletonList(store));
        when(courierStoreLogRepository.findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier())).thenReturn(Optional.of(courierStoreLog));

        courierStoreLocationService.updateCourierLocation(courierLocationLog);

        InOrder inOrder = Mockito.inOrder(storeService, courierStoreLogRepository, courierStoreLogRepository);
        inOrder.verify(storeService).getAllStore();
        inOrder.verify(courierStoreLogRepository).findTopByStoreAndCourierOrderByIdDesc(store, courierLocationLog.getCourier());
        inOrder.verify(courierStoreLogRepository).save(any(CourierStoreLog.class));
    }

    private CourierLocationLog createCourierLocationLog() {
        CourierLocationLog courierLocationLog = new CourierLocationLog();
        courierLocationLog.setId(1L);
        courierLocationLog.setCourier(createCourier());
        courierLocationLog.setLatitude(33.3333333);
        courierLocationLog.setLongitude(33.3333333);
        courierLocationLog.setTravelDistance(0.0);
        courierLocationLog.setCreateDate(Date.from(Instant.parse("2000-01-01T00:00:00.000Z")));
        return courierLocationLog;
    }

    private Courier createCourier() {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("hakkı");
        courier.setSurname("bulut");
        courier.setCreateDate(Date.from(Instant.parse("2000-01-01T00:00:00.000Z")));
        return courier;
    }

    private Store createStore() {
        Store store = new Store();
        store.setId(1L);
        store.setLatitude(33.3333333);
        store.setLongitude(33.3333333);
        store.setName("MİGROS JET - KARTAL");
        store.setCreateDate(Date.from(Instant.parse("2000-01-01T00:00:00.000Z")));
        return store;
    }
}
