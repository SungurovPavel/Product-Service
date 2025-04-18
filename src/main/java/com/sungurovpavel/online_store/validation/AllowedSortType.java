package com.sungurovpavel.online_store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedSortTypeValidator.class)
public @interface AllowedSortType {
    String message() default "Недопустимый тип сортировки. Доступны только: price, newest, rating, reviews";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
