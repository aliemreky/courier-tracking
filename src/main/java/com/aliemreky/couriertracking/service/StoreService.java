package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.request.CreateStoreRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    Store create(CreateStoreRequest request);

    Boolean delete(Long id);

    Store findStoreById(Long id);

    List<Store> getAllStore();
}
