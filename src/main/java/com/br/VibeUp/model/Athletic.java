package com.br.VibeUp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "athletic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Athletic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private int rank;
    private int score;
    private String fileUrl;

    public void incrementScore() {
        this.score++;
    }
}
