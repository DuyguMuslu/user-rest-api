package com.restapi.service;

import com.restapi.domain.*;
import com.restapi.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignService {

    final UserRepository userRepository;
    final PetRepository petRepository;
    final ModelMapper modelMapper;

    @Autowired
    public AssignService(PetRepository petRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createPetWithUser(Long userId, PetDto petDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new ObjectNotFoundException("No user found for this id-" + userId);
        Pet savedPet=petRepository.save(modelMapper.map(petDto,Pet.class));
        userOptional.get().getPetList().add(savedPet);
        return modelMapper.map(userOptional.get(),UserDto.class);
    }

    public UserDto updateUserOfPet(Long userId, long petId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new ObjectNotFoundException("No user found for this id-" + userId);
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (!petOptional.isPresent())
            throw new ObjectNotFoundException("No pet found for this id-" + petId);
        User user = userOptional.get();
        user.getPetList().add(petOptional.get());
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }
}
