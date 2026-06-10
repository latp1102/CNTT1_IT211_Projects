package org.example.project.exception;

public class CourtNotFoundException extends RuntimeException{
    public CourtNotFoundException(String message){
        super(message);
    }
}
