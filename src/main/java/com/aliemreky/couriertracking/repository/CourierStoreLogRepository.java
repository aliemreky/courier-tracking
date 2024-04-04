package com.aliemreky.couriertracking.repository;

import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.entity.CourierStoreLog;
import com.aliemreky.couriertracking.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierStoreLogRepository extends JpaRepository<CourierStoreLog, Long> {
    Optional<CourierStoreLog> findTopByStoreAndCourierOrderByIdDesc(Store store, Courier courier);
}
