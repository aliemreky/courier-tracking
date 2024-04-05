package com.aliemreky.couriertracking.service.impl;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.model.request.CreateCourierRequest;
import com.aliemreky.couriertracking.repository.CourierRepository;
import com.aliemreky.couriertracking.service.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public Courier createCourier(CreateCourierRequest request) {
        Courier courier = new Courier();
        courier.setName(request.getName());
        courier.setSurname(request.getSurname());

        Courier savedCourier = courierRepository.save(courier);

        log.info("Courier ({}) created successfully.", request.getName() + request.getSurname());
        return savedCourier;
    }

    @Override
    public Boolean deleteCourier(Long id) {
        try {
            courierRepository.deleteById(id);
            log.info("Courier(" + id + ") deleted successfully");
        } catch (Exception ex) {
            log.info("Courier(" + id + ") has not been deleted");
            return false;
        }
        return true;
    }

    @Override
    public Optional<Courier> getCourierById(Long id) {
        return courierRepository.findById(id);
    }

    @Override
    public List<Courier> getAllCourier() {
        return courierRepository.findAll();
    }
}
