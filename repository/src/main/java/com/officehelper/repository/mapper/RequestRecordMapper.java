package com.officehelper.repository.mapper;

import com.officehelper.domain.Request;
import com.officehelper.jooq.tables.records.RequestRecord;
import com.officehelper.jooq.tables.records.UserRecord;
import com.officehelper.repository.converter.RequestStatusConverter;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Timestamp;

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

        Timestamp orderDate = requestRecord.getOrderDate();
        request.setOrderDate(orderDate == null ? null : orderDate.toLocalDateTime());
        Timestamp receptionDate = requestRecord.getReceptionDate();
        request.setReceptionDate(receptionDate == null ? null : receptionDate.toLocalDateTime());

        request.setQuantity(requestRecord.getQuantity());
        request.setStatus(requestStatusConverter.from(requestRecord.getStatus()));
        request.setTitle(requestRecord.getTitle());
        request.setUrl(requestRecord.getUrl());
        return request;
    }
}
