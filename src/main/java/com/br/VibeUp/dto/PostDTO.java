package com.br.VibeUp.dto;

import java.util.Date;

public record PostDTO(
        String id,
        String title,
        int likes,
        Date dateOfPost,
        String userId,
        String username,
        String fileUrl
) {}