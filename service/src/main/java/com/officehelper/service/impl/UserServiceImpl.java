package com.officehelper.service.impl;

import com.officehelper.domain.User;
import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.domain.exception.DuplicateEntityException;
import com.officehelper.repository.UserRepository;
import com.officehelper.service.UserService;
import com.officehelper.service.command.AddUserCommand;
import com.officehelper.service.command.UpdateUserCommand;
import com.officehelper.service.mapper.UserCommandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String USER_ID_NOT_FOUND_MESSAGE = "User with id %d does not exist";
    private static final String USER_EMAIL_ALREADY_EXISTS_MESSAGE = "A user with email %s already exists";

    private UserRepository userRepository;
    private UserCommandMapper mapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, UserCommandMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User save(AddUserCommand command) {
        User user = mapper.toUser(command);
        logger.info("saving user {}", user);
        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            logger.warn("unable to save {}", user);
            throw new DuplicateEntityException(String.format(USER_EMAIL_ALREADY_EXISTS_MESSAGE, user.getEmail()));
        }
    }

    @Override
    @Transactional
    public void update(UpdateUserCommand command) {
        User user = mapper.toUser(command);
        logger.info("updating user {}", user);
        try {
            if (!userRepository.update(user)) {
                logger.warn("unable to find user {}", user);
                throw new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, user.getId()));
            }
        } catch (DuplicateKeyException e) {
            logger.warn("unable to update {}", user);
            throw new DuplicateEntityException(String.format(USER_EMAIL_ALREADY_EXISTS_MESSAGE, user.getEmail()));
        }
    }

    @Override
    @Transactional
    public User delete(long id) {
        logger.info("deleting user with id {}", id);
        return userRepository.delete(id).orElseThrow(() -> {
            logger.warn("unable to delete user with id {}", id);
            return new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, id));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getOne(long id) {
        return userRepository.findOne(id).orElseThrow(() -> {
            logger.warn("unable to find user with id {}", id);
            return new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, id));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            logger.warn("unable to find user with email {}", email);
            return new DataNotFoundException("User with email " + email + " does not exist");
        });
    }
}
