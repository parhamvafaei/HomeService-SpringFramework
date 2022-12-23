package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpertNotConfirmedException extends RuntimeException{
    public ExpertNotConfirmedException(String message) {
        super(message);
    }
}
