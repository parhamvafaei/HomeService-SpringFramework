package com.maktab.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileReaderException extends RuntimeException{
    public FileReaderException(String message) {
        super(message);
    }
}
