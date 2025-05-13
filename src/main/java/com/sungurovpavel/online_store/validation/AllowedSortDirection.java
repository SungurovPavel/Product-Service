package com.sungurovpavel.online_store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedSortDirectionValidator.class)
public @interface AllowedSortDirection {
    String message() default "Направление сортировки должно быть 'asc' или 'desc'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

