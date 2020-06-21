package com.restapi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDto {
    private Long petID;
    String name;
    Integer age ;
}
