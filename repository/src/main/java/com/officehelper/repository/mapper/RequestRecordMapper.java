package com.officehelper.repository.mapper;

import com.officehelper.domain.Request;
import com.officehelper.jooq.tables.records.RequestRecord;
import com.officehelper.repository.UserRepository;
import com.officehelper.repository.converter.RequestStatusConverter;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.officehelper.jooq.Tables.REQUEST;

/**
 * Created by 3ck0o on 2/12/2017.
 */

@Component
public class RequestRecordMapper implements RecordMapper<RequestRecord, Request> {

    @Inject
    private RequestStatusConverter requestStatusConverter;

    @Inject
    private UserRepository userRepository;

    @Override
    public Request map(RequestRecord requestRecord) {
        Request request = new Request();
        request.setUser(userRepository.get(requestRecord.getUserId().longValue()));
        request.setCreationDate(requestRecord.getCreationDate());
        request.setDescription(requestRecord.getDescription());
        request.setId(requestRecord.getId().longValue());
        request.setOrderDate(requestRecord.getOrderDate());
        request.setQuantity(requestRecord.getQuantity());
        request.setReceptionDate(requestRecord.getReceptionDate());
        request.setStatus(requestRecord.getValue(REQUEST.STATUS, requestStatusConverter));
        request.setTitle(requestRecord.getTitle());
        request.setUrl(requestRecord.getUrl());
        return request;
    }

    public void setRequestStatusConverter(RequestStatusConverter requestStatusConverter) {
        this.requestStatusConverter = requestStatusConverter;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
