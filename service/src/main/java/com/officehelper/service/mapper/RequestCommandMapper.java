package com.officehelper.service.mapper;

import com.officehelper.domain.Request;
import com.officehelper.domain.User;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;
import org.springframework.stereotype.Component;

@Component
public class RequestCommandMapper {
    public Request toRequest(AddRequestCommand command) {
        Request request = new Request();
        request.setDescription(command.getDescription());
        request.setQuantity(command.getQuantity());
        request.setTitle(command.getTitle());
        request.setUrl(command.getUrl());
        setRequestAuthor(command, request);
        return request;
    }

    public Request toRequest(UpdateRequestCommand command) {
        Request request = toRequest((AddRequestCommand) command);
        request.setId(command.getId());
        return request;
    }

    private void setRequestAuthor(AddRequestCommand command, Request request) {
        User user = new User();
        user.setId(command.getUserId());
        request.setUser(user);
    }
}
