package com.aliemreky.couriertracking.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateStoreRequest {

    @NotNull
    @Length(min = 3, max = 64)
    private String storeName;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double latitude;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double longitude;
}
