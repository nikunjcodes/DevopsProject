package com.devops.ticketservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightScheduleDto {
   private Long id;
   private Long flightId;
   private LocalDateTime departureTime;
   private LocalDateTime arrivalTime;
   private String status;
   private String flightNumber;
   private String origin;
   private String destination;
   private Integer availableSeats;
   private Double price;
}
