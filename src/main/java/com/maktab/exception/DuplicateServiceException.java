package com.maktab.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateServiceException extends RuntimeException{
    public DuplicateServiceException(String message) {
        super(message);
    }
}
