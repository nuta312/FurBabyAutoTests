package com.furbaby.pojo.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Breed {
    private int id;
    private String name;

}
