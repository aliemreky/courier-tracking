package com.aliemreky.couriertracking.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCourierRequest {

    @NotNull
    @Length(min = 3, max = 64)
    private String name;

    @NotNull
    @Length(min = 3, max = 64)
    private String surname;
}
