package com.cafe_brewtopia.api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sizeKey; // e.g., "small", "medium", "large"
    private Double price;   // e.g., 120.0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Coffee coffee;
}
