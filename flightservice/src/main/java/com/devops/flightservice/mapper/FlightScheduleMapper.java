package com.devops.flightservice.mapper;

import com.devops.flightservice.dto.FlightScheduleDto;
import com.devops.flightservice.model.Flight;
import com.devops.flightservice.model.FlightSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightScheduleMapper {
    FlightScheduleMapper INSTANCE = Mappers.getMapper(FlightScheduleMapper.class);

    @Mapping(source = "flight.id", target = "flightId")
    @Mapping(source = "flight.flightNumber", target = "flightNumber")
    @Mapping(source = "flight.origin", target = "origin")
    @Mapping(source = "flight.destination", target = "destination")
    @Mapping(source = "flight.availableSeats", target = "availableSeats")
    @Mapping(source = "flight.price", target = "price")
    FlightScheduleDto toDto(FlightSchedule schedule);

    List<FlightScheduleDto> toDtos(List<FlightSchedule> schedules);
}
