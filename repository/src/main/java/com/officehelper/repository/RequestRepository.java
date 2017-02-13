package com.officehelper.repository;

import com.officehelper.domain.Request;
import com.officehelper.domain.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository {

    Request save(Request request);

    Request update(Request request);

    boolean delete(long id);

    List<Request> findAll();

    Optional<Request> findOne(long id);

    Request get(long id);

    List<Request> findByUser(long userId);

    List<Request> findByStatus(RequestStatus status);

}
