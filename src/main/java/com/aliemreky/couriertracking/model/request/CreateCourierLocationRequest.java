package com.aliemreky.couriertracking.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCourierLocationRequest {

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double latitude;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double longitude;
}
