package com.devops.ticketservice.service;

import com.devops.ticketservice.dto.CreateTicketRequest;
import com.devops.ticketservice.dto.TicketDto;

public interface TicketService {
    TicketDto getTicketById(Long ticketId);
    TicketDto createTicket(CreateTicketRequest request);

    /**
     * Cancels a ticket by updating the status to 'CANCELLED'.
     * I could have deleted the ticket from the db but decided to do a soft delete
     * so that users can see their past bookings
     * @param ticketId ID of the ticket to be cancelled
     */
    void cancelTicket(Long ticketId);
}
