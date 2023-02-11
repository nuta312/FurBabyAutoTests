package com.furbaby.common;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Headers {

    RequestSpecification requestSpecification;

    public ContentType acceptJson(){
        return ContentType.JSON;
    }
}
