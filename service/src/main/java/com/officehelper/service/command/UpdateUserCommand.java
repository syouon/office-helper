package com.officehelper.service.command;

public class UpdateUserCommand extends AddUserCommand {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
