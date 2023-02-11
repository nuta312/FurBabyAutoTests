package com.furbaby.pojo.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNewPetResponse {
    private String name;
    private String sex;
    private String dateOfBirth;
    private String about;
    private String color;
    private int id;
    private int ownerId;
    private Category category;
    private Breed breed;


}
