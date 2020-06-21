package com.restapi.service;

import com.restapi.domain.Pet;
import com.restapi.domain.PetDto;
import com.restapi.domain.PetRepository;
import com.restapi.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    final PetRepository petRepository;
    final ModelMapper modelMapper;

    @Autowired
    public PetService(PetRepository petRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

   public PetDto createPet(PetDto petDto) {
       return convertToDto(petRepository.save(convertToEntity(petDto)));
   }

    public List<PetDto> retrieveAllPets(){
        return petRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PetDto retrievePet(long id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        if (!petOptional.isPresent())
            throw new ObjectNotFoundException("No pet found for this id-" + id);

        return convertToDto(petOptional.get());
    }

    public PetDto updatePet(String name, Integer age, long id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        if (!petOptional.isPresent())
            throw new ObjectNotFoundException("No pet found for this id-" + id);
        Pet pet = petOptional.get();
        pet.setName(name);
        pet.setAge(age);
        return convertToDto(petRepository.save(pet));
    }

    private Pet convertToEntity(PetDto petDto) {
        return modelMapper.map(petDto, Pet.class);
    }

    private PetDto convertToDto(Pet pet) {
        return modelMapper.map(pet, PetDto.class);
    }
}
