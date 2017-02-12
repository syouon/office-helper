package com.officehelper.service.impl;

import com.officehelper.domain.Request;
import com.officehelper.domain.RequestStatus;
import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.repository.RequestRepository;
import com.officehelper.service.RequestService;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;
import com.officehelper.service.mapper.RequestCommandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by 3ck0o on 2/12/2017.
 */

@Service
public class RequestServiceImpl implements RequestService {

    private static final String REQUEST_ID_NOT_FOUND_MESSAGE = "Request with id %d does not exist";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    RequestCommandMapper requestCommandMapper;

    @Inject
    RequestRepository requestRepository;

    @Override
    @Transactional
    public Request save(AddRequestCommand requestCommand) {
        Request request = requestCommandMapper.toRequest(requestCommand);
        request.setStatus(RequestStatus.NEW);
        request.setCreationDate(new Date());
        return requestRepository.save(request);
    }

    @Override
    @Transactional
    public Request update(UpdateRequestCommand requestCommand) {
        Request request = requestCommandMapper.toRequest(requestCommand);
        if(!requestRepository.update(request)) {
            logger.warn("unable to find request {}", request);
            throw new DataNotFoundException(String.format(REQUEST_ID_NOT_FOUND_MESSAGE, request.getId()));
        }
        return request;
    }

    @Override
    @Transactional
    public Request delete(long id) {
        return requestRepository.delete(id).orElseThrow(() -> {
            logger.warn("unable to delete request with id {}", id);
            return new DataNotFoundException(String.format(REQUEST_ID_NOT_FOUND_MESSAGE, id));
        });
    }

    @Override
    @Transactional
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    @Transactional
    public Request getOne(long id) {
        return requestRepository.findOne(id).orElseThrow(() -> {
            logger.warn("unable to find request with id {}", id);
            return new DataNotFoundException(String.format(REQUEST_ID_NOT_FOUND_MESSAGE, id));
        });
    }
}
