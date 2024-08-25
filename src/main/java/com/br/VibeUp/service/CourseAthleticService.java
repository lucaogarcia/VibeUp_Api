package com.br.VibeUp.service;

import com.br.VibeUp.dto.CourseAthleticDTO;
import com.br.VibeUp.model.CourseAthletic;
import com.br.VibeUp.repositories.CourseAthleticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseAthleticService {

    @Autowired
    private CourseAthleticRepository courseAthleticRepository;

    public List<CourseAthleticDTO> getAllCourseAthletics() {
        return courseAthleticRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseAthleticDTO createCourseAthletic(CourseAthleticDTO courseAthleticDTO) {
        CourseAthletic courseAthletic = new CourseAthletic();
        courseAthletic.setCourseName(courseAthleticDTO.courseName());
        courseAthletic.setAthleticName(courseAthleticDTO.athleticName());

        courseAthletic = courseAthleticRepository.save(courseAthletic);
        return convertToDTO(courseAthletic);
    }

    private CourseAthleticDTO convertToDTO(CourseAthletic courseAthletic) {
        return new CourseAthleticDTO(
                courseAthletic.getId(),
                courseAthletic.getCourseName(),
                courseAthletic.getAthleticName()
        );
    }
}