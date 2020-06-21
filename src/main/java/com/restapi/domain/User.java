package com.restapi.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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




}
