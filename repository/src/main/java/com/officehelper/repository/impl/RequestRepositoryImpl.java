package com.officehelper.repository.impl;

import com.officehelper.domain.Request;
import com.officehelper.domain.RequestStatus;
import com.officehelper.jooq.tables.records.RequestRecord;
import com.officehelper.repository.RequestRepository;
import com.officehelper.repository.mapper.RequestRecordMapper;
import org.jooq.DSLContext;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.officehelper.jooq.Tables.REQUEST;

/**
 * Created by 3ck0o on 2/12/2017.
 */

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    @Inject
    private DSLContext create;

    @Inject
    private RequestRecordMapper requestRecordMapper;

    @Override
    public Request save(Request request) {
        RequestRecord requestRecord = create.newRecord(REQUEST, request);
        requestRecord.store();
        return requestRecordMapper.map(requestRecord);
    }

    @Override
    public boolean update(Request request) {
        RequestRecord requestRecord = create.newRecord(REQUEST, request);
        return create.executeUpdate(requestRecord) > 0;
    }

    @Override
    public Optional<Request> delete(long id) {
        Optional<Request> request = findOne(id);
        if (request.isPresent()) {
            create.newRecord(REQUEST, request.get()).delete();
            return request;
        }
        return Optional.empty();
    }

    @Override
    public List<Request> findAll() {
        return create.selectFrom(REQUEST).fetch(requestRecordMapper);
    }

    @Override
    public Optional<Request> findOne(long id) {
        return create.selectFrom(REQUEST)
                .where(REQUEST.ID.equal(ULong.valueOf(id)))
                .fetchOptional(requestRecordMapper);
    }

    @Override
    public List<Request> findByUser(long userId) {
        return create.selectFrom(REQUEST)
                .where(REQUEST.USER_ID.equal(ULong.valueOf(userId)))
                .fetch(requestRecordMapper);
    }

    @Override
    public List<Request> findByStatus(RequestStatus status) {
        return create.selectFrom(REQUEST)
                .where(REQUEST.STATUS.equal(status.toString()))
                .fetch(requestRecordMapper);
    }

    public void setCreate(DSLContext create) {
        this.create = create;
    }

    public void setRequestRecordMapper(RequestRecordMapper requestRecordMapper) {
        this.requestRecordMapper = requestRecordMapper;
    }
}
