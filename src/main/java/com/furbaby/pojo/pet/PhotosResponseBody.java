package com.furbaby.pojo.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosResponseBody {
    List<Photos> photos;
}
