package com.devops.flightservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight_schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String status;
}
