package com.br.VibeUp.controller;

import com.br.VibeUp.dto.CourseAthleticDTO;
import com.br.VibeUp.service.CourseAthleticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courseAthletics")
public class CourseAthleticController {

    @Autowired
    private CourseAthleticService courseAthleticService;

    @GetMapping
    public List<CourseAthleticDTO> getAllCourseAthletics() {
        return courseAthleticService.getAllCourseAthletics();
    }

    @PostMapping("/create")
    public ResponseEntity<CourseAthleticDTO> createCourseAthletic(@RequestBody CourseAthleticDTO courseAthleticDTO){
        CourseAthleticDTO createdCourseAthletic = courseAthleticService.createCourseAthletic(courseAthleticDTO);
        return ResponseEntity.ok(createdCourseAthletic);
    }
}