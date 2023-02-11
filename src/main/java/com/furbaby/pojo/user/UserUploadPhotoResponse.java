package com.furbaby.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUploadPhotoResponse {

    private int id;
    private String name;
    private String url;
}
