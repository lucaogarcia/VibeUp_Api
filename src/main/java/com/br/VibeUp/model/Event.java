package com.br.VibeUp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String subtitle;
    private Date date;
    private String timeStart;
    private String timeEnd;
    private String description;
    private String address;
    private double price;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "athletic_id")
    private Athletic athletic;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sold_tickets")
    private int soldTickets;
}
