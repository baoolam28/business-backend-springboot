package com.onestep.business_management.Exeption;

public class InvalidAccountExeption extends RuntimeException{
    public InvalidAccountExeption(String message) {
        super(message);
    }
}
