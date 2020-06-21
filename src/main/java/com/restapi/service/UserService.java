package com.restapi.service;

import com.restapi.domain.User;
import com.restapi.domain.UserDto;
import com.restapi.domain.UserRepository;
import com.restapi.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createUser(UserDto userDto) {
        return convertToDto(userRepository.save(convertToEntity(userDto)));
    }

    public List<UserDto> retrieveAllUsers(){
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserDto retrieveUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            throw new ObjectNotFoundException("No user found for this id-" + id);

        return convertToDto(userOptional.get());
    }

    public UserDto updateUserAddress(String address, long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            throw new ObjectNotFoundException("No user found for this id-" + id);
        User user = userOptional.get();
        user.setCurrentAddress(address);
        return convertToDto(userRepository.save(user));
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
