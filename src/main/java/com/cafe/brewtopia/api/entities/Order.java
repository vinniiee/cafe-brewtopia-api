package com.cafe.brewtopia.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {



    @ManyToOne
    private Person person;

    @Embedded
    private Cart cart;

    private String status;
    private LocalDateTime eta;


}