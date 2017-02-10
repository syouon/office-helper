package com.officehelper.service.mapper;

import com.officehelper.domain.User;
import com.officehelper.service.command.AddUserCommand;
import com.officehelper.service.command.UpdateUserCommand;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMapper {

    public User toUser(AddUserCommand command) {
        return new User(command.getEmail());
    }

    public User toUser(UpdateUserCommand command) {
        User user = toUser((AddUserCommand) command);
        user.setId(command.getId());
        return user;
    }
}
