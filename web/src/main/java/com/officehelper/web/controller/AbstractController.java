package com.officehelper.web.controller;

import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.domain.exception.DuplicateEntityException;
import com.officehelper.domain.exception.InvalidRequestStatusException;
import com.officehelper.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AbstractController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    private ErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEntityException.class)
    private ErrorResponse handleDuplicateEntityException(DuplicateEntityException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidRequestStatusException.class)
    private ErrorResponse handleInvalidRequestStatusException(InvalidRequestStatusException e) {
        return new ErrorResponse(e.getMessage());
    }
}
