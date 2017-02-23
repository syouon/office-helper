package com.officehelper.repository.mapper;

import com.officehelper.domain.User;
import com.officehelper.jooq.tables.records.UserRecord;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRecordMapper implements RecordMapper<UserRecord, User> {

    @Override
    public User map(UserRecord userRecord) {
        return new User(
                userRecord.getId().longValue(),
                userRecord.getEmail()
        );
    }
}
