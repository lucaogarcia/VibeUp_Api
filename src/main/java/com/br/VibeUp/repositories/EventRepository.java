package com.br.VibeUp.repositories;

import com.br.VibeUp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByAthleticId(String athleticId);
}
