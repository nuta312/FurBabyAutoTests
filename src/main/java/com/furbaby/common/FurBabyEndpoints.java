package com.furbaby.common;

public enum FurBabyEndpoints {

    ADD_FOOD_CACHE("/food/cache/add"),
    LIST_FOOD_CACHE("/food/cache/list"),
    COMMIT_TO_DB("/food/commit"),
    CLEAR_CACHE("/food/resetcache"),
    UPDATE_FOOD("/food/cache/update"),
    USER_REGISTRATION("/user/registration");

    private final String endpoint;


    FurBabyEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }


}
