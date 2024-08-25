package com.br.VibeUp.repositories;

import com.br.VibeUp.model.Athletic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AthleticRepository extends JpaRepository<Athletic, String> {
    Optional<Athletic> findByName(String name);
}