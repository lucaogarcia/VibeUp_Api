package com.br.VibeUp.dto;

public record TicketDTO(
        String id,
        String eventId,
        String userId,
        String orderNumber
) {}