package com.devops.ticketservice.service;

import com.devops.ticketservice.dto.*;
import com.devops.ticketservice.mapper.TicketMapper;
import com.devops.ticketservice.model.Ticket;
import com.devops.ticketservice.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final RestTemplate restTemplate;

    private static final String FLIGHT_SERVICE_URL = "http://localhost:8081/flights";
    private  static final String USER_SERVICE_URL = "http://localhost:8080/users";

    @Override
    @Transactional(readOnly = true)
    public TicketDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with this id does not exist"));

        UserDto user;
        FlightDto flight;
        FlightScheduleDto schedule;

        try {
            user = restTemplate.getForObject(
                    USER_SERVICE_URL + "/{id}",
                    UserDto.class,
                    ticket.getUserId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid User ID provided");
        }

        try {
            flight = restTemplate.getForObject(
                    FLIGHT_SERVICE_URL + "/{id}",
                    FlightDto.class,
                    ticket.getFlightId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid Flight ID provided");
        }

        try {
            schedule = restTemplate.getForObject(
                    FLIGHT_SERVICE_URL + "/schedules/{id}",
                    FlightScheduleDto.class,
                    ticket.getFlightScheduleId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid Schedule ID provided");
        }

        return TicketMapper.INSTANCE.toDto(ticket, user, flight, schedule);
    }

    @Override
    @Transactional
    public TicketDto createTicket(CreateTicketRequest request) {
        UserDto user;
        FlightDto flight;
        FlightScheduleDto schedule;

        try {
             user = restTemplate.getForObject(
                    USER_SERVICE_URL + "/{id}",
                    UserDto.class,
                    request.getUserId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid User ID provided");
        }

        try {
            flight = restTemplate.getForObject(
                    FLIGHT_SERVICE_URL + "/{id}",
                    FlightDto.class,
                    request.getFlightId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid Flight ID provided");
        }

        try {
             schedule = restTemplate.getForObject(
                    FLIGHT_SERVICE_URL + "/schedules/{id}",
                    FlightScheduleDto.class,
                    request.getFlightScheduleId()
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Invalid Schedule ID provided");
        }

        String seatNumber = "S" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        Ticket ticket = Ticket.builder()
                .passengerName(user.getFirstName() + " " + user.getLastName())
                .seatNumber(seatNumber)
                .price(flight.getPrice())
                .flightId(request.getFlightId())
                .flightScheduleId(request.getFlightScheduleId())
                .userId(request.getUserId())
                .status("BOOKED")
                .bookingTime(LocalDateTime.now())
                .build();

        return TicketMapper.INSTANCE.toDto(ticketRepository.save(ticket), user, flight, schedule);
    }

    @Override
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with this id does not exist"));

        ticket.setStatus("CANCELLED");
        ticketRepository.save(ticket);
    }
}
