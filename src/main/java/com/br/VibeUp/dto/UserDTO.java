package com.br.VibeUp.dto;


public record UserDTO(
        String id,
        String name,
        String email,
        Integer age,
        Boolean admin,
        Integer likes,
        String course
){}
