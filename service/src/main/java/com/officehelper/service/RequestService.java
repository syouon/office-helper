package com.officehelper.service;

import com.officehelper.domain.Request;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;

import java.util.List;

public interface RequestService {

    Request save(AddRequestCommand requestCommand);

    Request update(UpdateRequestCommand requestCommand);

    Request delete(long id);

    List<Request> findAll();

    Request getOne(long id);

    void accept(Request request);

    void cancel(Request request);

    void refuse(Request request);

    void order(Request request);

    void setAsDelivered(Request request);

    void setAsNotDelivered(Request request);
}
