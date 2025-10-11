package com.cafe.brewtopia.api.dtos;


import com.cafe.brewtopia.api.entities.Order;
import lombok.Builder;

import java.util.List;

@Builder
public record CurrentUser(String name, String email, List<Order> orders, String address) {

}
