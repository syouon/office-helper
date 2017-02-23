package com.officehelper.repository.impl;

import com.officehelper.domain.Request;
import com.officehelper.domain.RequestStatus;
import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.jooq.tables.records.RequestRecord;
import com.officehelper.repository.RequestRepository;
import com.officehelper.repository.mapper.RequestRecordMapper;
import org.jooq.DSLContext;
import org.jooq.types.ULong;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.officehelper.jooq.Tables.REQUEST;
import static com.officehelper.jooq.tables.User.USER;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private DSLContext create;
    private RequestRecordMapper requestRecordMapper;

    @Inject
    public RequestRepositoryImpl(DSLContext create, RequestRecordMapper requestRecordMapper) {
        this.create = create;
        this.requestRecordMapper = requestRecordMapper;
    }

    @Override
    public Request save(Request request) {
        RequestRecord record = create.newRecord(REQUEST, request);
        record.store();
        return get(record.getId().longValue());
    }

    @Override
    public Request update(Request request) {
        RequestRecord record = create.newRecord(REQUEST, request);
        if (create.executeUpdate(record) == 0) {
            throw new DataNotFoundException(String.format("Impossible to update request [%d]", request.getId()));
        }
        return get(request.getId());
    }

    @Override
    public boolean delete(long id) {
        return create.delete(REQUEST)
                .where(REQUEST.ID.equal(ULong.valueOf(id)))
                .execute() > 0;
    }

    @Override
    public List<Request> findAll() {
        return create.select()
                .from(REQUEST)
                .join(USER).on(USER.ID.equal(REQUEST.USER_ID))
                .fetch(requestRecordMapper);
    }

    @Override
    public Optional<Request> findOne(long id) {
        return create.select()
                .from(REQUEST)
                .join(USER).on(USER.ID.equal(REQUEST.USER_ID))
                .where(REQUEST.ID.equal(ULong.valueOf(id)))
                .fetchOptional(requestRecordMapper);
    }

    @Override
    public Request get(long id) {
        return findOne(id).orElseThrow(() -> new DataNotFoundException(String.format("Impossible to find request [%d]", id)));
    }

    @Override
    public List<Request> findByUser(long userId) {
        return create.select()
                .from(REQUEST)
                .join(USER).on(USER.ID.equal(REQUEST.USER_ID))
                .where(REQUEST.USER_ID.equal(ULong.valueOf(userId)))
                .fetch(requestRecordMapper);
    }

    @Override
    public List<Request> findByStatus(RequestStatus status) {
        return create.select()
                .from(REQUEST)
                .join(USER).on(USER.ID.equal(REQUEST.USER_ID))
                .where(REQUEST.STATUS.equal(status.toString()))
                .fetch(requestRecordMapper);
    }
}
