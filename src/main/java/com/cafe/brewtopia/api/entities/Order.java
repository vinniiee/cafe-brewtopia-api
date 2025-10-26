package com.cafe.brewtopia.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order extends BaseEntity {

    @ManyToOne
    private Person person;



}
