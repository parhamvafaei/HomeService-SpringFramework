package com.maktab.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundServiceException extends RuntimeException {

    public NotFoundServiceException(String message) {
        super(message);
    }
}
