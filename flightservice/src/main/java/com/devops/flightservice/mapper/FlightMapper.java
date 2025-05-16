package com.devops.flightservice.mapper;

import com.devops.flightservice.dto.FlightDto;
import com.devops.flightservice.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightDto toDto(Flight flight);
    Flight toFlight(FlightDto flightDto);
    List<FlightDto> toDtos(List<Flight> flights);
}
