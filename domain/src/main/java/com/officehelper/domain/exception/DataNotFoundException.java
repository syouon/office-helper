package com.officehelper.domain.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
