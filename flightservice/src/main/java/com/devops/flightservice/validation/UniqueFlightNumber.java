package com.devops.flightservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlightNumberValidator.class)
public @interface UniqueFlightNumber {
    String message() default "A flight with this number already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
