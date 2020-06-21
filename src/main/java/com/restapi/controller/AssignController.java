package com.restapi.controller;


import com.restapi.domain.PetDto;
import com.restapi.domain.ResponseDto;
import com.restapi.service.AssignService;
import com.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Controller for the Rest Service requests about defining owner of the pet
 *
 * @author Duygu Muslu
 * @since  2020-06-17
 * @version 1.0
 */

@Controller
@RequestMapping
@Slf4j
public class AssignController {

    UserService userService;
    AssignService assignService;

    public AssignController(UserService userService,
                            AssignService assignService) {
        this.userService = userService;
        this.assignService = assignService;
    }

    @GetMapping(path ={"/users/{id}/pets"})
    public ResponseEntity<ResponseDto<?>> getAllPetsByUserId(@PathVariable(value = "id") Long userId) {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(userService.retrieveUser(userId)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path ={"/users/{id}/pets"})
    public ResponseEntity<ResponseDto<?>> createPet(@PathVariable (value = "id") Long userId,
                                 @Valid @RequestBody PetDto petDto) {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.CREATED.toString())
                .body(assignService.createPetWithUser(userId,petDto)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path ={"/users/{id}/pets/{petId}"})
    public ResponseEntity<ResponseDto<?>> setUserToPet(@PathVariable (value = "id") Long userId,
                                                    @PathVariable (value = "petId") Long petId) {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.ACCEPTED.toString())
                .body(assignService.updateUserOfPet(userId,petId)).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
