package com.officehelper.repository.mapper;

import com.officehelper.domain.Request;
import com.officehelper.domain.User;
import com.officehelper.jooq.tables.records.RequestRecord;
import com.officehelper.jooq.tables.records.UserRecord;
import com.officehelper.repository.converter.RequestStatusConverter;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RequestRecordMapper implements RecordMapper<Record, Request> {

    private RequestStatusConverter requestStatusConverter;

    private UserRecordMapper userRecordMapper;

    @Inject
    public RequestRecordMapper(RequestStatusConverter requestStatusConverter, UserRecordMapper userRecordMapper) {
        this.requestStatusConverter = requestStatusConverter;
        this.userRecordMapper = userRecordMapper;
    }

    @Override
    public Request map(Record record) {
        Request request = new Request();
        RequestRecord requestRecord = record.into(RequestRecord.class);
        request.setUser(userRecordMapper.map(record.into(UserRecord.class)));
        request.setCreationDate(requestRecord.getCreationDate().toLocalDateTime());
        request.setDescription(requestRecord.getDescription());
        request.setId(requestRecord.getId().longValue());
        request.setOrderDate(requestRecord.getOrderDate());
        request.setQuantity(requestRecord.getQuantity());
        request.setReceptionDate(requestRecord.getReceptionDate());
        request.setStatus(requestStatusConverter.from(requestRecord.getStatus()));
        request.setTitle(requestRecord.getTitle());
        request.setUrl(requestRecord.getUrl());
        return request;
    }
}
