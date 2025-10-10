package com.cafe.brewtopia.api.dtos;

public record RegisterRequest(String name, String username, String email, String password) {
}
