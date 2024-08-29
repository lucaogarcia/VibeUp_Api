package com.br.VibeUp.repositories;

import com.br.VibeUp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByAthleticId(String athleticId);

    Optional<Object> findByIdAndTitle(String id, String title);
}
