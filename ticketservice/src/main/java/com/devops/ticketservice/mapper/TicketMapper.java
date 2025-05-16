package com.devops.ticketservice.mapper;

import com.devops.ticketservice.dto.FlightDto;
import com.devops.ticketservice.dto.FlightScheduleDto;
import com.devops.ticketservice.dto.TicketDto;
import com.devops.ticketservice.dto.UserDto;
import com.devops.ticketservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    default TicketDto toDto(Ticket ticket, UserDto user, FlightDto flight, FlightScheduleDto schedule) {
        return TicketDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .passengerName(user.getFirstName() + " " + user.getLastName())
                .flightId(ticket.getFlightId())
                .flightScheduleId(ticket.getFlightScheduleId())
                .arrivalTime(schedule.getArrivalTime())
                .departureTime(schedule.getDepartureTime())
                .flightNumber(flight.getFlightNumber())
                .origin(flight.getOrigin())
                .price(flight.getPrice())
                .destination(flight.getDestination())
                .seatNumber(ticket.getSeatNumber())
                .status(ticket.getStatus())
                .bookingTime(ticket.getBookingTime())
                .build();
    }
    Ticket toTicket(TicketDto dto);

}
