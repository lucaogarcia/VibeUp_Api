package com.br.VibeUp.dto;

import java.util.Date;

public record PostDTO(
        String id,
        String description,
        String name,
        int likes,
        Date dateOfPost,
        String userId
){}
