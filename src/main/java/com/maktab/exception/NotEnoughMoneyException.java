package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
