package com.devops.ticketservice.service;

import com.devops.ticketservice.dto.*;
import com.devops.ticketservice.mapper.TicketMapper;
import com.devops.ticketservice.model.Ticket;
import com.devops.ticketservice.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;
    private UserDto userDto;
    private FlightDto flightDto;
    private FlightScheduleDto scheduleDto;
    private CreateTicketRequest createTicketRequest;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+1234567890")
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

        ticket = Ticket.builder()
                .id(1L)
                .userId(1L)
                .flightId(1L)
                .flightScheduleId(1L)
                .passengerName("John Doe")
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
    void getTicketById_Success() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(restTemplate.getForObject(anyString(), eq(UserDto.class), anyLong()))
                .thenReturn(userDto);
        when(restTemplate.getForObject(anyString(), eq(FlightDto.class), anyLong()))
                .thenReturn(flightDto);
        when(restTemplate.getForObject(anyString(), eq(FlightScheduleDto.class), anyLong()))
                .thenReturn(scheduleDto);

        TicketDto result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(ticket.getId(), result.getId());
        assertEquals(ticket.getPassengerName(), result.getPassengerName());
        assertEquals(ticket.getStatus(), result.getStatus());
        verify(ticketRepository).findById(1L);
    }

    @Test
    void getTicketById_NotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ticketService.getTicketById(1L));
        verify(ticketRepository).findById(1L);
    }

    @Test
    void getTicketById_UserNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(restTemplate.getForObject(anyString(), eq(UserDto.class), anyLong()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketById(1L));
    }

    @Test
    void createTicket_Success() {
        when(restTemplate.getForObject(anyString(), eq(UserDto.class), anyLong()))
                .thenReturn(userDto);
        when(restTemplate.getForObject(anyString(), eq(FlightDto.class), anyLong()))
                .thenReturn(flightDto);
        when(restTemplate.getForObject(anyString(), eq(FlightScheduleDto.class), anyLong()))
                .thenReturn(scheduleDto);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDto result = ticketService.createTicket(createTicketRequest);

        assertNotNull(result);
        assertEquals(ticket.getId(), result.getId());
        assertEquals(ticket.getPassengerName(), result.getPassengerName());
        assertEquals(ticket.getStatus(), result.getStatus());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void createTicket_UserNotFound() {
        when(restTemplate.getForObject(anyString(), eq(UserDto.class), anyLong()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(IllegalArgumentException.class, () -> ticketService.createTicket(createTicketRequest));
    }

    @Test
    void cancelTicket_Success() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        ticketService.cancelTicket(1L);

        verify(ticketRepository).findById(1L);
        verify(ticketRepository).save(any(Ticket.class));
        assertEquals("CANCELLED", ticket.getStatus());
    }

    @Test
    void cancelTicket_NotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ticketService.cancelTicket(1L));
        verify(ticketRepository).findById(1L);
    }
} 