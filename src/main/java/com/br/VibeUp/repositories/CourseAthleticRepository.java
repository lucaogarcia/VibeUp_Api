package com.br.VibeUp.repositories;

import com.br.VibeUp.model.CourseAthletic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseAthleticRepository extends JpaRepository<CourseAthletic, String> {
    Optional<CourseAthletic> findByCourseName(String courseName);
}