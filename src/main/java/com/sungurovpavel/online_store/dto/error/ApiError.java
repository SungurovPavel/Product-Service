package com.sungurovpavel.online_store.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private int statusCode;
    private String status;
    private String message;
}
