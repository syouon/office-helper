package com.officehelper.repository;

import com.officehelper.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean update(User user);

    Optional<User> delete(long id);

    List<User> findAll();

    Optional<User> findOne(long id);

    Optional<User> findByEmail(String email);
}
