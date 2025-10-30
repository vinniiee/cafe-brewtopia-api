package com.cafe.brewtopia.api.entities;

import com.cafe.brewtopia.api.entities.BaseEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CartItem extends BaseEntity {


    private String name;
    private String image;
    private String served;

    @ElementCollection
    private List<Double> prices;

    @ElementCollection
    private List<Integer> quantity;

}