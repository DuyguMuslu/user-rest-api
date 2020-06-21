package com.restapi.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long userID;
    @NotNull(message = "firstName can not be null")
    String firstName;
    @NotNull(message = "firstName can not be null")
    String lastName ;
    Date dateOfBirth;
    String currentAddress;
    List<PetDto> petList;
}
