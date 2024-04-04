package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.entity.CourierLocationLog;
import com.aliemreky.couriertracking.model.request.CreateCourierLocationRequest;
import com.aliemreky.couriertracking.repository.CourierLocationLogRepository;
import com.aliemreky.couriertracking.repository.CourierRepository;
import com.aliemreky.couriertracking.service.impl.CourierLocationLogServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierLocationLogServiceTest {

    @InjectMocks
    private CourierLocationLogServiceImpl courierLocationLogService;

    @Mock
    private CourierLocationLogRepository courierLocationLogRepository;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private List<CourierLocationObserver> observerCourierLocation;

    @Test
    public void testCreateCourierLocationLog() throws Exception {
        CreateCourierLocationRequest request = new CreateCourierLocationRequest();
        request.setLatitude(37.7749);
        request.setLongitude(-122.4194);

        Courier courier = new Courier();
        courier.setId(1L);

        CourierLocationLog courierLocationLog = new CourierLocationLog();
        courierLocationLog.setId(1L);
        courierLocationLog.setLatitude(37.7749);
        courierLocationLog.setLongitude(-122.4194);

        when(courierRepository.findById(1L)).thenReturn(Optional.of(courier));
        when(courierLocationLogRepository.save(any(CourierLocationLog.class))).thenReturn(courierLocationLog);

        CourierLocationLog savedCourierLocationLog = courierLocationLogService.create(1L, request);

        assertThat(savedCourierLocationLog).isNotNull();
        assertThat(request.getLatitude()).isEqualTo(savedCourierLocationLog.getLatitude());
        assertThat(request.getLongitude()).isEqualTo(savedCourierLocationLog.getLongitude());
    }

    @Test
    public void testFindLastByCourier() {
        Long courierId = 1L;
        CourierLocationLog expectedLocationLog = new CourierLocationLog();
        expectedLocationLog.setLatitude(33.33333);

        when(courierLocationLogRepository.findTopByCourierOrderByIdDesc(courierId))
                .thenReturn(List.of(expectedLocationLog));

        Optional<CourierLocationLog> result = courierLocationLogService.findLastByCourier(courierId);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isNotNull();
        assertThat(result.get().getLatitude()).isEqualTo(expectedLocationLog.getLatitude());
    }

    @Test
    public void testGetTotalTravelDistance() {
        Long courierId = 1L;
        double expectedDistance = 123.45;

        CourierLocationLog courierLocationLog = new CourierLocationLog();
        courierLocationLog.setTravelDistance(expectedDistance);

        when(courierLocationLogRepository.findByCourierId(courierId))
                .thenReturn(List.of(courierLocationLog));

        double totalDistance = courierLocationLogService.getTotalTravelDistance(courierId);

        assertThat(totalDistance).isEqualTo(123.45);
    }

}
