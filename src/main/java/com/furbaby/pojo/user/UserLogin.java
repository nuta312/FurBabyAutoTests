package com.furbaby.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLogin {


    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String dateOfBirth;
    private int postcode;
    private String phoneNumber;
    private int id;
    private boolean isNew;
    private String photo;
    private boolean isActive;

}
