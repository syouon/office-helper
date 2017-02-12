package com.officehelper.repository.converter;

import com.officehelper.domain.RequestStatus;
import org.jooq.impl.EnumConverter;
import org.springframework.stereotype.Component;

/**
 * Created by 3ck0o on 2/12/2017.
 */

@Component
public class RequestStatusConverter extends EnumConverter<String, RequestStatus> {
    public RequestStatusConverter() {
        super(String.class, RequestStatus.class);
    }
}
