package com.devops.flightservice.dto;

import com.devops.flightservice.validation.ValidDateRange;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange
public class FlightScheduleDto {
   private Long id;

   @NotBlank(message = "flight id is required")
   private Long flightId;

   @NotNull(message = "departure time is required")
   @FutureOrPresent(message = "departure time must be in present or future")
   private LocalDateTime departureTime;


   @NotNull(message = "arrival time is required")
   @FutureOrPresent(message = "arrival time must be in present or future")
   private LocalDateTime arrivalTime;

   private String status;

   private String flightNumber;
   private String origin;
   private String destination;
   private Integer availableSeats;
   private Double price;

}
