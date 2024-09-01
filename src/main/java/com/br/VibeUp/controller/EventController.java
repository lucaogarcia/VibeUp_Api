package com.br.VibeUp.controller;

import com.br.VibeUp.dto.EventDTO;
import com.br.VibeUp.model.Event;
import com.br.VibeUp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/createEvent/{athleticName}")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO,
                                                @PathVariable String athleticName) throws IOException {
        EventDTO newEvent = eventService.createEvent(eventDTO, athleticName);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping("/remainingTickets/{id}")
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