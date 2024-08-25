package com.br.VibeUp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_athletic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseAthletic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String courseName;
    private String athleticName;
}