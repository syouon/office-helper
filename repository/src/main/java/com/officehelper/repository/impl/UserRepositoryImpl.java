package com.officehelper.repository.impl;

import com.officehelper.domain.User;
import com.officehelper.jooq.tables.records.UserRecord;
import com.officehelper.repository.UserRepository;
import com.officehelper.repository.mapper.UserRecordMapper;
import org.jooq.DSLContext;
import org.jooq.types.ULong;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.officehelper.jooq.tables.User.USER;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext create;
    private final UserRecordMapper mapper;

    @Inject
    public UserRepositoryImpl(DSLContext create, UserRecordMapper mapper) {
        this.create = create;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserRecord userRecord = create.newRecord(USER, user);
        userRecord.store();
        return userRecord.map(mapper);
    }

    @Override
    public boolean update(User user) {
        Optional<UserRecord> recordOptional = create.fetchOptional(USER, USER.ID.equal(ULong.valueOf(user.getId())));
        if (recordOptional.isPresent()) {
            UserRecord userRecord = recordOptional.get();
            userRecord.setId(ULong.valueOf(user.getId()));
            userRecord.setEmail(user.getEmail());
            userRecord.update();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> delete(long id) {
        Optional<UserRecord> recordOptional = create.fetchOptional(USER, USER.ID.equal(ULong.valueOf(id)));
        if (recordOptional.isPresent()) {
            recordOptional.get().delete();
            return recordOptional.map(mapper::map);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return create.selectFrom(USER).fetch(mapper);
    }

    @Override
    public Optional<User> findOne(long id) {
        return create.selectFrom(USER)
                .where(USER.ID.equal(ULong.valueOf(id)))
                .fetchOptional(mapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return create.selectFrom(USER)
                .where(USER.EMAIL.equal(email))
                .fetchOptional(mapper);
    }
}
