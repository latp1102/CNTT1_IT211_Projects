package org.example.project.exception;

public class BookingConflictException extends RuntimeException{
    public BookingConflictException(String message) {
        super(message);
    }
}
