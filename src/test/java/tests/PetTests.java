package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.furbaby.pojo.pet.CreateNewPetResponse;
import com.furbaby.utils.ObjectConverter;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class PetTests extends BaseApiTest {


    @Test
    @Description("Verify that user can create new Pet")
    public void createNewPetTest() throws JsonProcessingException {
        response = petActions.createNewPet();
        Assert.assertEquals(response.getStatusCode(), 201);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), CreateNewPetResponse.class);
    }

    @Test
    @Description("Verify that user can see pet details")
    public void getPetDetailsTest() throws JsonProcessingException {
        response = petActions.getPetDetails();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), CreateNewPetResponse.class);
        Assert.assertNotNull(createNewPetResponse.getId());
    }

    @Test
    @Description("Verify that user can delete pet")
    public void deletePetTest() throws JsonProcessingException {
        response = petActions.deletePet();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    @Description("Verify that user can upload pets photo")
    public void uploadPetPhotoTest() throws IOException {
        response = petActions.uploadPetPhoto();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Verify that user can delete pets photo")
    public void deletePetPhotoTest() throws IOException {
        response = petActions.deletePetPhoto();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Verify that user can delete pets photo")
    public void getUserPetsTest() throws IOException {
        RequestSpecification requestSpecification1 = RestAssured.given();
        requestSpecification1.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet/user/111");
        requestSpecification1.contentType(ContentType.JSON);
        requestSpecification1.accept(ContentType.JSON);
        requestSpecification1.header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwidXNlcl9pZCI6MTU5LCJleHAiOjE2NzYwMDgyMjd9.yj9PVN-G9tRwFIzZ-GO35EE-m3HQvjB9kkLCyFJXgYY");

        response = requestSpecification1.request(Method.GET);
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
    }
}


