package com.aliemreky.couriertracking.api;


import com.aliemreky.couriertracking.entity.Store;
import com.aliemreky.couriertracking.model.request.CreateStoreRequest;
import com.aliemreky.couriertracking.model.response.ResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/store")
public interface StoreApi {

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseModel<Store> create(@RequestBody @Valid CreateStoreRequest request);

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseModel<Boolean> delete(@PathVariable @NotNull Long id);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseModel<Store> getStoreById(@PathVariable @NotNull Long id);

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    ResponseModel<List<Store>> getAllStore();
}
