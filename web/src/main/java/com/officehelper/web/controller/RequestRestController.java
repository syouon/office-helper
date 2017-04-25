package com.officehelper.web.controller;

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
public class RequestRestController extends AbstractController {

    private RequestService requestService;
    private final RequestDtoMapper dtoMapper;

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
    public RequestDto findOne(@PathVariable long id) {
        return dtoMapper.from(requestService.getOne(id));
    }

    @PostMapping("/{id}/accept")
    public void accept(@PathVariable long id) {
        requestService.accept(requestService.getOne(id));
    }

    @PostMapping("/{id}/refuse")
    public void refuse(@PathVariable long id) {
        requestService.refuse(requestService.getOne(id));
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable long id) {
        requestService.cancel(requestService.getOne(id));
    }

    @PostMapping("/{id}/order")
    public void order(@PathVariable long id) {
        requestService.order(requestService.getOne(id));
    }

    @PostMapping("/{id}/delivery/ok")
    public void requestDelivered(@PathVariable long id) {
        requestService.setAsDelivered(requestService.getOne(id));
    }

    @PostMapping("/{id}/delivery/failed")
    public void requestNotDelivered(@PathVariable long id) {
        requestService.setAsNotDelivered(requestService.getOne(id));
    }
}
