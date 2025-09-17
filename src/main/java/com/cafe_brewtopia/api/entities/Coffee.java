package com.cafe_brewtopia.api.entities;

import com.cafe_brewtopia.api.enums.Served;
import com.cafe_brewtopia.api.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Served served;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    List<Double> prices;
}
