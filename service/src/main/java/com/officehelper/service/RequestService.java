package com.officehelper.service;

import com.officehelper.domain.Request;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;

import java.util.List;

/**
 * Created by 3ck0o on 2/12/2017.
 */
public interface RequestService {

    Request save(AddRequestCommand requestCommand);

    Request update(UpdateRequestCommand requestCommand);

    Request delete(long id);

    List<Request> findAll();

    Request getOne(long id);
}
