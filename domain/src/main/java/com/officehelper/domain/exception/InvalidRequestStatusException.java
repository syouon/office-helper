package com.officehelper.domain.exception;

import com.officehelper.domain.RequestStatus;

import java.util.Collection;
import java.util.stream.Collectors;

public class InvalidRequestStatusException extends RuntimeException {

    public InvalidRequestStatusException(Collection<RequestStatus> nextStatus) {
        super("Incompatible status, possible statuses are: " + convertToString(nextStatus));
    }

    private static String convertToString(Collection<RequestStatus> status) {
        return status.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(", "));
    }
}
