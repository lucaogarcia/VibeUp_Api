package com.br.VibeUp.dto;

import java.util.Date;

public record RegisterRequestDTO(
        String name,
        String username,
        String email,
        String password,
        Date date_of_birth,
        int likes,
        boolean admin,
        String course) {}
