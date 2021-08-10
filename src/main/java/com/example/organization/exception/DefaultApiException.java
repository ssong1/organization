package com.example.organization.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultApiException {

    private final String code;
    private final String message;
}
