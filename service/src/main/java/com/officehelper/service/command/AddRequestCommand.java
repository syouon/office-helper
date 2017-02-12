package com.officehelper.service.command;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 3ck0o on 2/12/2017.
 */
public class AddRequestCommand {

    @NotBlank
    private Long userId;

    @NotBlank
    private String title;

    private String description;

    private String url;

    @NotBlank
    private Integer quantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
