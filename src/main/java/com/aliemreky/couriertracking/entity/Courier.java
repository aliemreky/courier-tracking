package com.aliemreky.couriertracking.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courier")
public class Courier extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;
}
