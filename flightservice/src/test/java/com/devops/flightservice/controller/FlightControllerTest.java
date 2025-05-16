package com.devops.flightservice.controller;

import com.devops.flightservice.dto.FlightDto;
import com.devops.flightservice.dto.FlightScheduleDto;
import com.devops.flightservice.model.Flight;
import com.devops.flightservice.service.FlightService;
import com.devops.flightservice.repository.FlightRepository;
import com.devops.flightservice.repository.FlightScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightService flightService;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private FlightScheduleRepository flightScheduleRepository;

    private FlightDto flightDto;
    private FlightScheduleDto scheduleDto;
    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = Flight.builder()
                .id(1L)
                .flightNumber("FL123")
                .origin("New York")
                .destination("London")
                .capacity(200)
                .availableSeats(150)
                .price(500.0)
                .build();

        flightDto = FlightDto.builder()
                .id(1L)
                .flightNumber("FL123")
                .origin("New York")
                .destination("London")
                .capacity(200)
                .availableSeats(150)
                .price(500.0)
                .build();

        scheduleDto = FlightScheduleDto.builder()
                .id(1L)
                .flightId(1L)
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalTime(LocalDateTime.now().plusDays(1).plusHours(8))
                .status("SCHEDULED")
                .build();
    }

    @Test
    void getAllFlights_Success() throws Exception {
        when(flightService.getAllFlights("asc")).thenReturn(Arrays.asList(flightDto));

        mockMvc.perform(get("/")
                        .param("sort", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].flightNumber").value(flightDto.getFlightNumber()));
    }

    @Test
    void getFlightById_Success() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(flightDto);

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()));
    }

    @Test
    void getFlightById_NotFound() throws Exception {
        when(flightService.getFlightById(1L)).thenThrow(new EntityNotFoundException("Flight not found"));

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFlightSchedules_Success() throws Exception {
        when(flightService.getFlightSchedules(eq(1L), any())).thenReturn(Arrays.asList(scheduleDto));

        mockMvc.perform(get("/{id}/schedules", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value(scheduleDto.getStatus()));
    }

    @Test
    void getFlightSchedule_Success() throws Exception {
        when(flightService.getFlightSchedule(1L)).thenReturn(scheduleDto);

        mockMvc.perform(get("/schedules/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(scheduleDto.getStatus()));
    }

    @Test
    void createFlight_Success() throws Exception {
        when(flightService.createFlight(any(FlightDto.class))).thenReturn(flightDto);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flight.getFlightNumber()));
    }

    @Test
    void createFlight_ValidationError() throws Exception {
        FlightDto invalidFlight = FlightDto.builder().build(); // Missing required fields

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFlight)))
                .andExpect(status().isBadRequest());
    }
}
