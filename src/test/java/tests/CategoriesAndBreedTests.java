package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoriesAndBreedTests extends BaseApiTest{


    @Test
    @Description("Verify GET Categories returning valid data")
    public void getCategoriesTest() throws JsonProcessingException {
        String accessToken = userActions.getUserAccessToken();
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet/categories");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        response = requestSpecification.request(Method.GET);
        Assert.assertEquals(response.getStatusCode(),200);

        // TODO need to check the ID and Category name
    }

    @Test
    @Description("Verify GET Breeds returning valid data")
    public void getBreedTest() throws JsonProcessingException {
        String accessToken = userActions.getUserAccessToken();
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet/breeds/1");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        response = requestSpecification.request(Method.GET);
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);

        // TODO need to check the ID and Category name
    }

}
