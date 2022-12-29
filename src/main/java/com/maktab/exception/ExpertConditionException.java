package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpertConditionException extends RuntimeException{
    public ExpertConditionException(String message) {
        super(message);
    }
}
