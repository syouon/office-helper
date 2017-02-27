package com.officehelper.web.dto.mapper;


import com.officehelper.domain.Request;
import com.officehelper.web.dto.RequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestDtoMapper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public RequestDto from(Request request) {
        if (request == null) {
            return null;
        }

        RequestDto dto = new RequestDto();
        dto.setId(request.getId());
        dto.setUser(request.getUser());
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setUrl(request.getUrl());
        dto.setQuantity(request.getQuantity());
        dto.setStatus(request.getStatus());

        LocalDateTime creationDate = request.getCreationDate();
        dto.setCreationDate(creationDate == null ? null : creationDate.format(dateTimeFormatter));

        LocalDateTime orderDate = request.getOrderDate();
        dto.setOrderDate(orderDate == null ? null : orderDate.format(dateTimeFormatter));

        LocalDateTime receptionDate = request.getReceptionDate();
        dto.setReceptionDate(receptionDate == null ? null : receptionDate.format(dateTimeFormatter));

        return dto;
    }
}
