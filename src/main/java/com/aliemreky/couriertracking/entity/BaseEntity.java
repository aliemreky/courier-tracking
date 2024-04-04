package com.aliemreky.couriertracking.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity<T> implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @Column(name = "create_date")
    private Date createDate;

    @Version
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long version;

    @PrePersist
    protected void prePersist() {
        if (this.createDate == null) createDate = new Date();
    }
}
