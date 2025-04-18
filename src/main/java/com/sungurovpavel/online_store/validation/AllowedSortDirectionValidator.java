package com.sungurovpavel.online_store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedSortDirectionValidator implements ConstraintValidator<AllowedSortDirection, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && (value.equalsIgnoreCase("asc") || value.equalsIgnoreCase("desc"));
    }
}
