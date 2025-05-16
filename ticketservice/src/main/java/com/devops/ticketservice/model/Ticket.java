package com.devops.ticketservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long flightId;
    private Long flightScheduleId;
    private String passengerName;
    private String seatNumber;
    private Double price;
    private String status;
    private LocalDateTime bookingTime;
}
