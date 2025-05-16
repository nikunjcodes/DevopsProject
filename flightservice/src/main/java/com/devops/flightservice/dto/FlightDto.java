package com.devops.flightservice.dto;

import com.devops.flightservice.validation.UniqueFlightNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank(message = "flight number is required")
    @UniqueFlightNumber
    private String flightNumber;

    @NotBlank(message = "origin is required")
    private String origin;

    @NotBlank(message = "destination is required")
    private String destination;

    @NotNull(message = "capacity is required")
    @Positive(message = "capacity must be positive")
    private Integer capacity;

    @NotNull(message = "Available seats is required")
    @Positive(message = "available seats must be positive")
    private Integer availableSeats;


    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private Double price;

}
