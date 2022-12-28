package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundPersonException extends RuntimeException{
    public NotFoundPersonException(String message) {
        super(message);
    }
}
