package com.br.VibeUp.service;

import com.br.VibeUp.dto.EventDTO;
import com.br.VibeUp.model.Event;
import com.br.VibeUp.model.Athletic;
import com.br.VibeUp.repositories.EventRepository;
import com.br.VibeUp.repositories.AthleticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AthleticRepository athleticRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EventDTO createEvent(EventDTO eventDTO, String athleticName) {
        Athletic athletic = athleticRepository.findByName(athleticName)
                .orElseThrow(() -> new RuntimeException("Athletic not found"));

        Event event = new Event();
        event.setTitle(eventDTO.title());
        event.setSubtitle(eventDTO.subtitle());
        event.setDate(eventDTO.date());
        event.setTimeStart(eventDTO.timeStart());
        event.setTimeEnd(eventDTO.timeEnd());
        event.setDescription(eventDTO.description());
        event.setAddress(eventDTO.address());
        event.setPrice(eventDTO.price());
        event.setAthletic(athletic);
        event.setQuantity(eventDTO.quantity());
        event.setSoldTickets(eventDTO.soldTickets());

        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    public EventDTO convertToDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getSubtitle(),
                event.getDate(),
                event.getTimeStart(),
                event.getTimeEnd(),
                event.getDescription(),
                event.getAddress(),
                event.getPrice(),
                event.getAthletic().getId(),
                event.getQuantity(),
                event.getSoldTickets()
        );
    }

    public int getRemainingTickets(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getQuantity() - event.getSoldTickets();
    }

    public List<EventDTO> getEventsByAthleticId(String athleticId) {
        List<Event> events = eventRepository.findByAthleticId(athleticId);

        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}