package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundAnyOrderException extends RuntimeException{
    public NotFoundAnyOrderException(String message) {
        super(message);
    }
}
