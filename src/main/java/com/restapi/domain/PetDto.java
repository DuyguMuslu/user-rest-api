package com.restapi.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PetDto {
    private Long petID;
    @NotNull(message = "name can not be null")
    String name;
    Integer age ;
}
