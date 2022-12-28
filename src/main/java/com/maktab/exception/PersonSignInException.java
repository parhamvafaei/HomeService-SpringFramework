package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonSignInException extends RuntimeException{
    public PersonSignInException(String message) {
        super(message);
    }
}
