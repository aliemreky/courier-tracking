package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;
import com.aliemreky.couriertracking.repository.CourierRepository;
import com.aliemreky.couriertracking.service.impl.CourierServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierServiceTest {

    @InjectMocks
    private CourierServiceImpl courierService;

    @Mock
    private CourierRepository courierRepository;

    @Test
    public void create_shouldCreateCourier() {
        Courier courier = getDummyCourier();
        when(courierRepository.save(any(Courier.class))).thenReturn(courier);

        CreateCourierRequest request = new CreateCourierRequest();
        request.setName("hakkı");
        request.setSurname("bulut");
        courierService.createCourier(request);

        InOrder inOrder = Mockito.inOrder(courierRepository);
        inOrder.verify(courierRepository).save(any(Courier.class));
    }

    @Test
    public void delete_shouldDeleteCourier() {
        courierService.deleteCourier(1L);

        //then
        InOrder inOrder = Mockito.inOrder(courierRepository);
        inOrder.verify(courierRepository).deleteById(1L);
    }

    @Test
    public void findById_shouldGetCourier() {
        //given
        Courier dummyCourier = getDummyCourier();
        when(courierRepository.findById(dummyCourier.getId())).thenReturn(Optional.of(dummyCourier));

        //when
        Optional<Courier> result = courierService.getCourierById(dummyCourier.getId());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(dummyCourier.getId());
        assertThat(result.get().getName()).isEqualTo(dummyCourier.getName());
        assertThat(result.get().getSurname()).isEqualTo(dummyCourier.getSurname());
        assertThat(result.get().getCreateDate()).isEqualTo(dummyCourier.getCreateDate());
    }

    @Test
    public void findById_shouldGetThrow_WhenCourierIsNull() {
        Long courierId = 1L;
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> courierService.getCourierById(courierId));
        assertEquals("1 courier not found", exception.getMessage());
    }

    @Test
    public void findAll_shouldGetCouriers() {
        //given
        List<Courier> couriers = new ArrayList<>();
        couriers.add(getDummyCourier());
        couriers.add(getDummyCourier());
        when(courierRepository.findAll()).thenReturn(couriers);

        //when
        List<Courier> resultList = courierService.getAllCourier();

        //then
        assertThat(resultList.size()).isEqualTo(couriers.size());
        assertThat(resultList.get(0)).isEqualTo(couriers.get(0));
        assertThat(resultList.get(1)).isEqualTo(couriers.get(1));
    }

    private Courier getDummyCourier() {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("hakkı");
        courier.setSurname("bulut");
        courier.setCreateDate(Date.from(Instant.parse("2000-01-01T00:00:00.000Z")));
        return courier;
    }
}
