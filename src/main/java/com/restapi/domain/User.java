package com.restapi.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"firstName", "lastName"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    @NotNull(message = "NotNull.firstName")
    String firstName;

    @NotNull(message = "NotNull.lastName")
    String lastName ;

    Date dateOfBirth;
    String currentAddress;

    @OneToMany(targetEntity=Pet.class,cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    List<Pet> petList;



}
