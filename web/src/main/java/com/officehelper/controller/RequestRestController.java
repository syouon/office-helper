package com.officehelper.controller;

import com.officehelper.domain.Request;
import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.domain.exception.DuplicateEntityException;
import com.officehelper.service.RequestService;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by 3ck0o on 2/12/2017.
 */

@RestController
@RequestMapping("/requests")
public class RequestRestController {

    @Inject
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody AddRequestCommand command, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Request>(requestService.save(command), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Request> update(@RequestBody UpdateRequestCommand command, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Request>(requestService.update(command), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public Request delete(@PathVariable long id) {
        return requestService.delete(id);
    }

    @GetMapping
    public List<Request> findAll() {
        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public Request findOne(@PathVariable Long id) {
        return requestService.getOne(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    private String handleDataNotFoundException(DataNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEntityException.class)
    private String handleDuplicateEntityException(DuplicateEntityException e) {
        return e.getMessage();
    }
    
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
