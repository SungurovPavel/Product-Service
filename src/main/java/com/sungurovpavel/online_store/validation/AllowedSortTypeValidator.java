package com.sungurovpavel.online_store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class AllowedSortTypeValidator implements ConstraintValidator<AllowedSortType, String> {
    private static final Set<String> ALLOWED_TYPES = Set.of("price", "newest", "rating", "reviews");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && ALLOWED_TYPES.contains(value.toLowerCase());
    }
}
