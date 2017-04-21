package com.officehelper.domain;

import com.officehelper.domain.exception.InvalidRequestStatusException;

import java.util.*;

public enum RequestStatus {
    NEW {
        @Override
        public Set<RequestStatus> nextPossibleStatus() {
            return buildNextStatus(ACCEPTED, REFUSED, CANCELED);
        }
    },
    ACCEPTED {
        @Override
        public Set<RequestStatus> nextPossibleStatus() {
            return buildNextStatus(ORDERED, CANCELED);
        }
    },
    ORDERED {
        @Override
        public Set<RequestStatus> nextPossibleStatus() {
            return buildNextStatus(DELIVERED, NOT_DELIVERED);
        }
    },
    REFUSED,
    CANCELED,
    NOT_DELIVERED,
    DELIVERED;


    /**
     * Define all next possible status for the current one (default: none).
     * Should be overridden each time a new status with next ones is added.
     * @return a collection of next reachable status
     */
    public Set<RequestStatus> nextPossibleStatus() {
        return Collections.emptySet();
    }

    public void verifyNextStatus(RequestStatus nextStatus) {
        Collection<RequestStatus> nextPossibleStatus = nextPossibleStatus();
        if (!nextPossibleStatus.contains(nextStatus)) {
            throw new InvalidRequestStatusException(nextPossibleStatus);
        }
    }

    private static Set<RequestStatus> buildNextStatus(RequestStatus... statuses) {
        return new HashSet<>(Arrays.asList(statuses));
    }
}
