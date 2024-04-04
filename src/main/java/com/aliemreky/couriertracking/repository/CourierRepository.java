package com.aliemreky.couriertracking.repository;

import com.aliemreky.couriertracking.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
