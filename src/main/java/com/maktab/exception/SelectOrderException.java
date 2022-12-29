package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SelectOrderException extends RuntimeException{
    public SelectOrderException(String message) {
        super(message);
    }
}
