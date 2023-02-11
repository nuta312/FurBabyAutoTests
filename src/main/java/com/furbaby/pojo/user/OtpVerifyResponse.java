package com.furbaby.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpVerifyResponse {

    private List<UserLogin> userLogins;
    private String accessToken;
    private String refreshToken;
}
