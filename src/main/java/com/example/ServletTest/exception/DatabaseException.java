package com.example.ServletTest.exception;

public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }

    public DatabaseException(String errorMessage, Throwable exception) {
        super(errorMessage, exception);
    }

}
