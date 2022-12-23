package com.maktab.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpertAddException extends RuntimeException{
    public ExpertAddException(String str)
    {

        super(str);
    }
}
