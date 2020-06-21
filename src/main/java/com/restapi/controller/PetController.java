package com.restapi.controller;

import com.restapi.domain.PetDto;
import com.restapi.domain.ResponseDto;
import com.restapi.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping
@Slf4j
public class PetController {

    final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(path ={"/pets"})
    public ResponseEntity<Object> createPet(@Valid @RequestBody PetDto petDto){
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.CREATED.toString())
                .body(petService.createPet(petDto).toString()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path ={"/pets"})
    public ResponseEntity<ResponseDto<?>> retrieveAllPets() {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(petService.retrieveAllPets()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path ={"/pets/{id}"})
    public ResponseEntity<ResponseDto<?>> retrievePet(@PathVariable long id) {
        ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(petService.retrievePet(id)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path ={"/pets/{id}/name/{name}/age/{age}"})
    public ResponseEntity<Object> updatePet(@PathVariable long id,@PathVariable String name,@PathVariable Integer age) {
           ResponseDto<?> response = ResponseDto.builder()
                .status(HttpStatus.OK.toString())
                .body(petService.updatePet(name,age,id)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
