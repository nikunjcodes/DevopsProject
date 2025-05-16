package com.devops.ticketservice.controller;

import com.devops.ticketservice.dto.*;
import com.devops.ticketservice.service.TicketService;
import com.devops.ticketservice.repository.TicketRepository;
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
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private RestTemplate restTemplate;

    private TicketDto ticketDto;
    private CreateTicketRequest createTicketRequest;

    @BeforeEach
    void setUp() {
        ticketDto = TicketDto.builder()
                .id(1L)
                .userId(1L)
                .flightId(1L)
                .flightScheduleId(1L)
                .passengerName("John Doe")
                .flightNumber("FL123")
                .origin("New York")
                .destination("London")
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalTime(LocalDateTime.now().plusDays(1).plusHours(8))
                .seatNumber("A1")
                .price(500.0)
                .status("BOOKED")
                .bookingTime(LocalDateTime.now())
                .build();

        createTicketRequest = CreateTicketRequest.builder()
                .userId(1L)
                .flightId(1L)
                .flightScheduleId(1L)
                .build();
    }

    @Test
    void getTicketById_Success() throws Exception {
        when(ticketService.getTicketById(1L)).thenReturn(ticketDto);

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.passengerName").value(ticketDto.getPassengerName()))
                .andExpect(jsonPath("$.flightNumber").value(ticketDto.getFlightNumber()))
                .andExpect(jsonPath("$.status").value(ticketDto.getStatus()));
    }

    @Test
    void getTicketById_NotFound() throws Exception {
        when(ticketService.getTicketById(1L)).thenThrow(new EntityNotFoundException("Ticket not found"));

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTicket_Success() throws Exception {
        when(ticketService.createTicket(any(CreateTicketRequest.class))).thenReturn(ticketDto);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTicketRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.passengerName").value(ticketDto.getPassengerName()))
                .andExpect(jsonPath("$.flightNumber").value(ticketDto.getFlightNumber()))
                .andExpect(jsonPath("$.status").value(ticketDto.getStatus()));
    }

    @Test
    void createTicket_ValidationError() throws Exception {
        CreateTicketRequest invalidRequest = CreateTicketRequest.builder().build(); // Missing required fields

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelTicket_Success() throws Exception {
        doNothing().when(ticketService).cancelTicket(1L);

        mockMvc.perform(delete("/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(ticketService).cancelTicket(1L);
    }

    @Test
    void cancelTicket_NotFound() throws Exception {
        doThrow(new EntityNotFoundException("Ticket not found"))
                .when(ticketService).cancelTicket(1L);

        mockMvc.perform(delete("/{id}", 1L))
                .andExpect(status().isNotFound());
    }
} 