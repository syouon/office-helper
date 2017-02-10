package com.officehelper.repository.mapper;

import com.officehelper.domain.User;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

import static com.officehelper.jooq.tables.User.USER;

@Component
public class UserRecordMapper implements RecordMapper<Record, User> {

    @Override
    public User map(Record record) {
        return new User(
                record.getValue(USER.ID).longValue(),
                record.getValue(USER.EMAIL)
        );
    }
}
