package com.officehelper.service.impl;

import com.officehelper.domain.Request;
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
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private static final String REQUEST_ID_NOT_FOUND_MESSAGE = "Request [%d] does not exist";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestCommandMapper requestCommandMapper;
    private RequestRepository requestRepository;

    @Inject
    public RequestServiceImpl(RequestCommandMapper requestCommandMapper, RequestRepository requestRepository) {
        this.requestCommandMapper = requestCommandMapper;
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public Request save(AddRequestCommand requestCommand) {
        Request request = requestCommandMapper.toRequest(requestCommand);
        Request newRequest = requestRepository.save(request);
        logger.info("Added new request [{}]", newRequest.getId());
        return newRequest;
    }

    @Override
    @Transactional
    public Request update(UpdateRequestCommand requestCommand) {
        Request request = requestCommandMapper.toRequest(requestCommand);
        logger.info("Updated request [{}]", request.getId());
        return requestRepository.update(request);
    }

    @Override
    @Transactional
    public Request delete(long id) {
        Request request = requestRepository.get(id);
        if(!requestRepository.delete(id)) {
            logger.warn("Unable to find request [{}] for deletion", id);
            throw new DataNotFoundException(String.format(REQUEST_ID_NOT_FOUND_MESSAGE, id));
        }
        return request;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Request getOne(long id) {
        return requestRepository.findOne(id).orElseThrow(() -> {
            logger.warn("Unable to find request [{}]", id);
            return new DataNotFoundException(String.format(REQUEST_ID_NOT_FOUND_MESSAGE, id));
        });
    }
}
