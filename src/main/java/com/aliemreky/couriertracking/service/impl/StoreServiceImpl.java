package com.aliemreky.couriertracking.service.impl;

import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.request.CreateStoreRequest;
import com.aliemreky.couriertracking.repository.StoreRepository;
import com.aliemreky.couriertracking.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    @CacheEvict(value = "stores", allEntries = true)
    public Store create(CreateStoreRequest request) {
        Store store = new Store();
        store.setName(request.getStoreName());
        store.setLatitude(request.getLatitude());
        store.setLongitude(request.getLongitude());

        store = storeRepository.save(store);

        log.info("Store ({}) created successfully.", store.getId());

        return store;
    }

    @Override
    @CacheEvict(value = "stores", allEntries = true)
    public Boolean delete(Long id) {
        try {
            storeRepository.deleteById(id);
            log.info("Store (" + id + ") deleted successfully");
        } catch (Exception ex) {
            log.info("Store (" + id + ") has not been deleted. Message : " + ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    @Cacheable(value = "stores", key = "#id")
    public Store findStoreById(Long id) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            return storeOptional.get();
        } else {
            log.info("{} id store not found", id);
            throw new RuntimeException(id + " store not found");
        }
    }

    @Override
    @Cacheable("stores")
    public List<Store> getAllStore() {
        return storeRepository.findAll();
    }
}
