package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeleteExpertException extends RuntimeException {
    public DeleteExpertException(String message) {
        super(message);
    }
}
