package com.matejamusa.personal_finance.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}