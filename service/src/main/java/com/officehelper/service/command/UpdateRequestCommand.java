package com.officehelper.service.command;

/**
 * Created by 3ck0o on 2/12/2017.
 */
public class UpdateRequestCommand extends AddRequestCommand {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
