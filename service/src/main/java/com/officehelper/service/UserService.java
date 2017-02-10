package com.officehelper.service;

import com.officehelper.domain.User;
import com.officehelper.service.command.AddUserCommand;
import com.officehelper.service.command.UpdateUserCommand;

import java.util.List;

public interface UserService {

    User save(AddUserCommand command);

    void update(UpdateUserCommand command);

    User delete(long id);

    List<User> findAll();

    User getOne(long id);

    User getByEmail(String email);
}
