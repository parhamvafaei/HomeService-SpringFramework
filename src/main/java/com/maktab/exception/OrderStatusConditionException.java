package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderStatusConditionException extends RuntimeException{
    public OrderStatusConditionException(String message) {
        super(message);
    }
}
