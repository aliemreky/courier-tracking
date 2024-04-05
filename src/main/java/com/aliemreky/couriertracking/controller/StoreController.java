package com.aliemreky.couriertracking.controller;


import com.aliemreky.couriertracking.api.StoreApi;
import com.aliemreky.couriertracking.entity.Courier;
import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.request.CreateStoreRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import com.aliemreky.couriertracking.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class StoreController implements StoreApi {

    private final StoreService storeService;

    @Override
    public ResponseModel<Store> create(@RequestBody @Valid CreateStoreRequest request){
        return ResponseModel.success(storeService.create(request));
    }

    @Override
    public ResponseModel<Boolean> delete(@PathVariable @NotNull Long id){
        return ResponseModel.success(storeService.delete(id));
    }

    @Override
    public ResponseModel<Store> getStoreById(@PathVariable @NotNull Long id){
        Optional<Store> store = storeService.findStoreById(id);

        return store.isPresent() ? ResponseModel.success(store.get()) :
                ResponseModel.warning(null, "The Store " + id + " not found!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseModel<List<Store>> getAllStore(){
        return ResponseModel.success(storeService.getAllStore());
    }
}
