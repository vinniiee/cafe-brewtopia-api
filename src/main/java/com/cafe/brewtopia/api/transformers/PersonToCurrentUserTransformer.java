package com.cafe.brewtopia.api.transformers;

import com.cafe.brewtopia.api.dtos.CurrentUser;
import com.cafe.brewtopia.api.entities.Person;

public class PersonToCurrentUserTransformer {

    public static CurrentUser toCurrentUser(Person person) {
        return CurrentUser.builder()
                .name(person.getName())
                .email(person.getEmail())
                .address(person.getAddress())
                .orders(person.getOrders())
                .build();
    }
}