package com.br.VibeUp.service;

import com.br.VibeUp.dto.TicketDTO;
import com.br.VibeUp.model.Event;
import com.br.VibeUp.model.Ticket;
import com.br.VibeUp.model.User;
import com.br.VibeUp.model.Athletic;
import com.br.VibeUp.model.CourseAthletic;
import com.br.VibeUp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AthleticRepository athleticRepository;

    @Autowired
    private CourseAthleticRepository courseAthleticRepository;

    @Autowired
    private AthleticService athleticService;

    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Event event = eventRepository.findById(ticketDTO.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(ticketDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CourseAthletic courseAthletic = courseAthleticRepository.findByCourseName(user.getCourse())
                .orElseThrow(() -> new RuntimeException("Athletic not found for user's course"));

        Athletic athletic = athleticRepository.findByName(courseAthletic.getAthleticName())
                .orElseThrow(() -> new RuntimeException("Athletic not found"));

        athletic.incrementScore();
        athleticRepository.save(athletic);

        athleticService.updateAthleticRank();

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setPrice(event.getPrice());
        ticket.setLocation(event.getAddress());
        ticket.setEventDate(event.getDate());
        ticket.setEventTime(concatenateEventTimes(event));
        ticket.setOrderNumber(generateOrderNumber());

        ticket = ticketRepository.save(ticket);
        return convertToDTO(ticket);
    }

    private String concatenateEventTimes(Event event) {
        String timeStart = event.getTimeStart() != null ? event.getTimeStart() : "";
        String timeEnd = event.getTimeEnd() != null ? event.getTimeEnd() : "";
        return timeStart + " - " + timeEnd;
    }

    private String generateOrderNumber() {
        Random random = new Random();
        int orderNumber = random.nextInt(90000000) + 10000000;
        return String.valueOf(orderNumber);
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getUser().getId(),
                ticket.getOrderNumber()
        );
    }
}