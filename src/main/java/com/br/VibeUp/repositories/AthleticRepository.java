package com.br.VibeUp.repositories;

import com.br.VibeUp.model.Athletic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AthleticRepository extends JpaRepository<Athletic,String> {
    Optional<Athletic> findByName(String name);
}
