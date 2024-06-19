package com.br.VibeUp.dto;


import java.util.Date;

public record UserDTO(
        String id,
        String name,
        String username,
        String email,
        Date date_of_birth,
        boolean admin,
        int likes,
        String course
){}
