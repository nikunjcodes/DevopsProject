package com.devops.flightservice.service;

import com.devops.flightservice.dto.FlightDto;
import com.devops.flightservice.dto.FlightScheduleDto;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<FlightDto> getAllFlights(String sortDirection);
    FlightDto getFlightById(Long id);
    List<FlightScheduleDto> getFlightSchedules(Long flightId, String dates);
    FlightDto createFlight(FlightDto flightDto);
    FlightScheduleDto getFlightSchedule(Long scheduleId);
}
