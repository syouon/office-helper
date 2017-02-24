package com.officehelper.web.controller;

import com.officehelper.domain.Request;
import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.web.dto.ErrorResponse;
import com.officehelper.service.RequestService;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestRestController {

    private RequestService requestService;

    @Inject
    public RequestRestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid AddRequestCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(requestService.save(command), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid UpdateRequestCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        requestService.update(command);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
    private ErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
