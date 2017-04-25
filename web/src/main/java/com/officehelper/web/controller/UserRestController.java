package com.officehelper.web.controller;

import com.officehelper.domain.User;
import com.officehelper.service.UserService;
import com.officehelper.service.command.AddUserCommand;
import com.officehelper.service.command.UpdateUserCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController extends AbstractController {

    private UserService userService;

    @Inject
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid AddUserCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.save(command), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid UpdateUserCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Email bad format", HttpStatus.BAD_REQUEST);
        }
        userService.update(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable long id) {
        return userService.delete(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable long id) {
        return userService.getOne(id);
    }
}
