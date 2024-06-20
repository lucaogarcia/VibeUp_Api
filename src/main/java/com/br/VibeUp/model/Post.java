package com.br.VibeUp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String name;
    private int likes;
    private Date dateOfPost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
