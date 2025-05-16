package com.devops.flightservice.repository;

import com.devops.flightservice.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

    @Query("select f from FlightSchedule f where f.flight.id = ?1 and f.departureTime >= ?2 and f.arrivalTime <= ?3")
    List<FlightSchedule> findAllByFlightIdAndDateRange(Long id, LocalDateTime departureTime, LocalDateTime arrivalTime);

    List<FlightSchedule> findByFlightId(Long id);
}