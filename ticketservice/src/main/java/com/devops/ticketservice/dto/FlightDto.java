package com.devops.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private Integer capacity;
    private Integer availableSeats;
    private Double price;
}
