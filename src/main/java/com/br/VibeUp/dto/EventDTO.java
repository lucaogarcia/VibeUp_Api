package com.br.VibeUp.dto;

import java.util.Date;

public record EventDTO(
        String id,
        String title,
        String subtitle,
        Date date,
        String timeStart,
        String timeEnd,
        String description,
        String address,
        double price,
        String athleticId,
        int quantity,
        int soldTickets,
        String fileUrl
) {}
