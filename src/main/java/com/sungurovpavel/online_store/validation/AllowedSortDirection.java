package com.sungurovpavel.online_store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedSortDirectionValidator.class)
public @interface AllowedSortDirection {
    String message() default "Направление сортировки должно быть 'asc' или 'desc'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

