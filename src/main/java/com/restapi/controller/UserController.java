package com.restapi.controller;

import com.restapi.domain.ResponseDto;
import com.restapi.domain.UserDto;
import com.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Controller for the Rest Service requests related to user definition
 *
 * @author Duygu Muslu
 * @since  2020-06-17
 * @version 1.0
 */

@Controller
@RequestMapping
@Slf4j
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path ={"/users"})
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto){
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.CREATED.toString())
                .body(userService.createUser(userDto)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path ={"/users"})
    public ResponseEntity<ResponseDto<?>> retrieveAllUsers() {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(userService.retrieveAllUsers()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path ={"/users/{id}"})
    public ResponseEntity<ResponseDto<?>> retrieveUser(@PathVariable long id) {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(userService.retrieveUser(id)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path ={"/users/{id}/address/{address}"})
    public ResponseEntity<Object> updateUserAddress(@PathVariable long id,@PathVariable String address) {
           ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(userService.updateUserAddress(address,id)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
