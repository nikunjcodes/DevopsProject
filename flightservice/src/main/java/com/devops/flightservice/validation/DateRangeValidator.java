package com.devops.flightservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDateTime departureTime = (LocalDateTime) new BeanWrapperImpl(value).getPropertyValue("departureTime");
        LocalDateTime arrivalTime = (LocalDateTime) new BeanWrapperImpl(value).getPropertyValue("arrivalTime");

        return arrivalTime.isAfter(departureTime);
    }
}
