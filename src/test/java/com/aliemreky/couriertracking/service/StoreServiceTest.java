package com.aliemreky.couriertracking.service;

import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.request.CreateStoreRequest;
import com.aliemreky.couriertracking.repository.StoreRepository;
import com.aliemreky.couriertracking.service.StoreService;
import com.aliemreky.couriertracking.service.impl.StoreServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreServiceImpl storeService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void create_shouldCreateStore() {
        Store store = createStore();
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        CreateStoreRequest request = new CreateStoreRequest();
        request.setStoreName("MIGROS JET - KARTAL");
        request.setLatitude(33.333333);
        request.setLongitude(23.333333);
        storeService.create(request);

        InOrder inOrder = Mockito.inOrder(storeRepository);
        inOrder.verify(storeRepository).save(any(Store.class));
    }

    @Test
    public void delete_shouldDeleteStore() {
        Long storeId = 1L;
        storeService.delete(storeId);

        InOrder inOrder = Mockito.inOrder(storeRepository);
        inOrder.verify(storeRepository).deleteById(storeId);
    }

    @Test
    public void findById_shouldGetStore() {
        Store store = createStore();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));

        Optional<Store> result = storeService.findStoreById(store.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(store.getId());
        assertThat(result.get().getName()).isEqualTo(store.getName());
        assertThat(result.get().getLatitude()).isEqualTo(store.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(store.getLongitude());
        assertThat(result.get().getCreateDate()).isEqualTo(store.getCreateDate());
    }

    @Test
    public void findById_shouldGetEmptyOptional_WhenStoreIsNull() {
        Long storeId = 99L;
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        Optional<Store> optionalStore = storeService.findStoreById(storeId);

        assertThat(optionalStore.isEmpty()).isTrue();
    }

    @Test
    public void findAll_shouldGetStores() {
        List<Store> stores = new ArrayList<>();
        stores.add(createStore());
        stores.add(createStore());
        when(storeRepository.findAll()).thenReturn(stores);

        List<Store> resultList = storeService.getAllStore();

        assertThat(resultList.size()).isEqualTo(stores.size());
        assertThat(resultList.get(0)).isEqualTo(stores.get(0));
        assertThat(resultList.get(1)).isEqualTo(stores.get(1));
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
