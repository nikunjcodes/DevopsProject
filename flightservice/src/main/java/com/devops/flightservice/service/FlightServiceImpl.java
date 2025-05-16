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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    @Override
    public List<FlightDto> getAllFlights(String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by("flightNumber").ascending() :
                Sort.by("flightNumber").descending();

        List<Flight> flights = flightRepository.findAll(sort);

        return FlightMapper.INSTANCE.toDtos(flights);
    }

    @Override
    public FlightDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight with this id does not exist"));

        return FlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    public List<FlightScheduleDto> getFlightSchedules(Long flightId, String dates) {
        flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight with this id does not exist"));

        List<FlightSchedule> schedules;

        if(dates != null) {
            String[] dateRange = dates.split(",");
            LocalDateTime startDate = LocalDateTime.parse(dateRange[0], DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime endDate = dateRange.length > 1 ?
                    LocalDateTime.parse(dateRange[1], DateTimeFormatter.ISO_DATE_TIME) :
                    startDate.plusDays(7);

            schedules = flightScheduleRepository.findAllByFlightIdAndDateRange(flightId, startDate, endDate);
        } else {
            schedules = flightScheduleRepository.findByFlightId(flightId);
        }

        return FlightScheduleMapper.INSTANCE.toDtos(schedules);
    }

    @Override
    public FlightScheduleDto getFlightSchedule(Long scheduleId) {
        FlightSchedule schedule = flightScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule with this id does not exist"));

        return FlightScheduleMapper.INSTANCE.toDto(schedule);
    }

    @Override
    @Transactional
    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = FlightMapper.INSTANCE.toFlight(flightDto);
        return FlightMapper.INSTANCE.toDto(flightRepository.save(flight));
    }
}
