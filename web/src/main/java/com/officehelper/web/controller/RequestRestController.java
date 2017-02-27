package com.officehelper.web.controller;

import com.officehelper.domain.exception.DataNotFoundException;
import com.officehelper.web.dto.ErrorResponse;
import com.officehelper.service.RequestService;
import com.officehelper.service.command.AddRequestCommand;
import com.officehelper.service.command.UpdateRequestCommand;
import com.officehelper.web.dto.RequestDto;
import com.officehelper.web.dto.mapper.RequestDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/requests")
public class RequestRestController {

    private RequestService requestService;
    private RequestDtoMapper dtoMapper;

    @Inject
    public RequestRestController(RequestService requestService, RequestDtoMapper dtoMapper) {
        this.requestService = requestService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<RequestDto> save(@RequestBody @Valid AddRequestCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RequestDto dto = dtoMapper.from(requestService.save(command));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
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
    public RequestDto delete(@PathVariable long id) {
        return dtoMapper.from(requestService.delete(id));
    }

    @GetMapping
    public List<RequestDto> findAll() {
        return requestService.findAll().stream()
                .map(dtoMapper::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RequestDto findOne(@PathVariable Long id) {
        return dtoMapper.from(requestService.getOne(id));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    private ErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
