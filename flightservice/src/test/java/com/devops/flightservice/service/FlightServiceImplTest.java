package com.devops.flightservice.service;

import com.devops.flightservice.dto.FlightDto;
import com.devops.flightservice.dto.FlightScheduleDto;
import com.devops.flightservice.mapper.FlightMapper;
import com.devops.flightservice.mapper.FlightScheduleMapper;
import com.devops.flightservice.model.Flight;
import com.devops.flightservice.model.FlightSchedule;
import com.devops.flightservice.repository.FlightRepository;
import com.devops.flightservice.repository.FlightScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight flight;
    private FlightDto flightDto;
    private FlightSchedule schedule;
    private FlightScheduleDto scheduleDto;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("FL123");
        flight.setOrigin("New York");
        flight.setDestination("London");
        flight.setPrice(500.0);
        flight.setCapacity(200);
        flight.setAvailableSeats(150);

        flightDto = FlightMapper.INSTANCE.toDto(flight);

        schedule = new FlightSchedule();
        schedule.setId(1L);
        schedule.setFlight(flight);
        schedule.setDepartureTime(LocalDateTime.now().plusDays(1));
        schedule.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(8));
        schedule.setStatus("SCHEDULED");

        scheduleDto = FlightScheduleMapper.INSTANCE.toDto(schedule);
    }

    @Test
    void getAllFlights_Success() {
        when(flightRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(flight));

        List<FlightDto> result = flightService.getAllFlights("asc");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(flight.getFlightNumber(), result.get(0).getFlightNumber());
        verify(flightRepository).findAll(any(Sort.class));
    }

    @Test
    void getFlightById_Success() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightDto result = flightService.getFlightById(1L);

        assertNotNull(result);
        assertEquals(flight.getFlightNumber(), result.getFlightNumber());
        verify(flightRepository).findById(1L);
    }

    @Test
    void getFlightById_NotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> flightService.getFlightById(1L));
        verify(flightRepository).findById(1L);
    }

    @Test
    void getFlightSchedules_WithDateRange_Success() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightScheduleRepository.findAllByFlightIdAndDateRange(eq(1L), any(), any()))
                .thenReturn(Arrays.asList(schedule));

        String dateRange = LocalDateTime.now() + "," + LocalDateTime.now().plusDays(7);
        List<FlightScheduleDto> result = flightService.getFlightSchedules(1L, dateRange);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(schedule.getId(), result.get(0).getId());
        verify(flightScheduleRepository).findAllByFlightIdAndDateRange(eq(1L), any(), any());
    }

    @Test
    void getFlightSchedules_WithoutDateRange_Success() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightScheduleRepository.findByFlightId(1L)).thenReturn(Arrays.asList(schedule));

        List<FlightScheduleDto> result = flightService.getFlightSchedules(1L, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(schedule.getId(), result.get(0).getId());
        verify(flightScheduleRepository).findByFlightId(1L);
    }

    @Test
    void getFlightSchedule_Success() {
        when(flightScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        FlightScheduleDto result = flightService.getFlightSchedule(1L);

        assertNotNull(result);
        assertEquals(schedule.getId(), result.getId());
        verify(flightScheduleRepository).findById(1L);
    }

    @Test
    void getFlightSchedule_NotFound() {
        when(flightScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> flightService.getFlightSchedule(1L));
        verify(flightScheduleRepository).findById(1L);
    }

    @Test
    void createFlight_Success() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        FlightDto result = flightService.createFlight(flightDto);

        assertNotNull(result);
        assertEquals(flight.getFlightNumber(), result.getFlightNumber());
        verify(flightRepository).save(any(Flight.class));
    }
} 