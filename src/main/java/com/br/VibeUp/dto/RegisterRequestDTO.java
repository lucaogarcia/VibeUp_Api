package com.br.VibeUp.dto;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Integer age,
        Integer likes,
        Boolean admin,
        String course) {}
