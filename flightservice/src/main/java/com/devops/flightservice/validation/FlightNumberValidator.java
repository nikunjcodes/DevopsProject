package com.devops.flightservice.validation;


import com.devops.flightservice.repository.FlightRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FlightNumberValidator implements ConstraintValidator<UniqueFlightNumber, String> {
    @Autowired
    private FlightRepository flightRepository;

    @Override
    public boolean isValid(String flightNumber, ConstraintValidatorContext context) {
        return !flightRepository.existsByFlightNumber(flightNumber);
    }
}
