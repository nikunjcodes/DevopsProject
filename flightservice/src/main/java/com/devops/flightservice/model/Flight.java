package com.devops.flightservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flights")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String origin;
    private String destination;
    private Double price;
    private Integer capacity;
    private Integer availableSeats;
}
