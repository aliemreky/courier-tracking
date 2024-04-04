package com.aliemreky.couriertracking.repository;

import com.aliemreky.couriertracking.entity.CourierLocationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierLocationLogRepository extends JpaRepository<CourierLocationLog, Long> {

    @Query(value = "select b from CourierLocationLog b where b.courier.id = :courierId order by b.id desc")
    List<CourierLocationLog> findTopByCourierOrderByIdDesc(@Param(value = "courierId") Long courierId);

    @Query(value = "select b from CourierLocationLog b where b.courier.id = :courierId")
    List<CourierLocationLog> findByCourierId(@Param(value = "courierId") Long courierId);

}
