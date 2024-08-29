package com.br.VibeUp.controller;

import com.br.VibeUp.dto.EventDTO;
import com.br.VibeUp.model.Event;
import com.br.VibeUp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/{athleticName}/create")
    public ResponseEntity<EventDTO> createEvent(@PathVariable String athleticName, @RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO, athleticName);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/{id}/remainingTickets")
    public ResponseEntity<Integer> getRemainingTickets(@PathVariable String id) {
        int remainingTickets = eventService.getRemainingTickets(id);
        return ResponseEntity.ok(remainingTickets);
    }

    @GetMapping("/athletic/{id}")
    public ResponseEntity<List<EventDTO>> getEventsByAthleticId(@PathVariable String id) {
        List<EventDTO> events = eventService.getEventsByAthleticId(id);
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/{id}/{title}")
    public void deleteEvent(@PathVariable String id, @PathVariable String title) {
        eventService.deleteEvent(id, title);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable String id, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(id, updatedEvent);
    }

}