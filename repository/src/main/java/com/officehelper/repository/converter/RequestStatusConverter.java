package com.officehelper.repository.converter;

import com.officehelper.domain.RequestStatus;
import org.jooq.impl.EnumConverter;
import org.springframework.stereotype.Component;

@Component
public class RequestStatusConverter extends EnumConverter<String, RequestStatus> {
    public RequestStatusConverter() {
        super(String.class, RequestStatus.class);
    }
}
