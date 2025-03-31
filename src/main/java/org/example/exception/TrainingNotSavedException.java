package org.example.exception;

public class TrainingNotSavedException extends RuntimeException {
    public TrainingNotSavedException(String message) {
        super(message);
    }
}
