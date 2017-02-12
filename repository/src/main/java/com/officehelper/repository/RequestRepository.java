package com.officehelper.repository;

import com.officehelper.domain.Request;
import com.officehelper.domain.RequestStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by 3ck0o on 2/12/2017.
 */
public interface RequestRepository {

    Request save(Request request);

    boolean update(Request request);

    Optional<Request> delete(long id);

    List<Request> findAll();

    Optional<Request> findOne(long id);

    List<Request> findByUser(long userId);

    List<Request> findByStatus(RequestStatus userId);

}
