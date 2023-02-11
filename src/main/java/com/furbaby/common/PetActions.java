package com.furbaby.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.furbaby.pojo.pet.CreateNewPetResponse;
import com.furbaby.pojo.pet.Photos;
import com.furbaby.pojo.pet.PhotosResponseBody;
import com.furbaby.utils.ObjectConverter;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

public class PetActions {

    RequestSpecification requestSpecification;
    RequestSpecification requestSpecification1;
    Response response;
    UserActions userActions = new UserActions();
    Faker faker = new Faker();

    public Response createNewPet() throws JsonProcessingException {
        String accessToken = userActions.getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);

        String payload = "{\n" +
                "  \"name\":" + "\"" + faker.dog().name() + "\"," + "\n" +
                "  \"sex\":" + "\"" + generateRandomPetGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + userActions.generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"about\":" + "\"" + faker.shakespeare().asYouLikeItQuote() + "\"," + "\n" +
                "  \"color\":" + "\"" + generatePetColor() + "\"," + "\n" +
                "  \"categoryId\":" + "" + generateRandomCategoryID() + "," + "\n" +
                "  \"breedId\":" + "" + generateRandomBreedID() + "\n" +
                "}";

        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST);
        return response = requestSpecification.request(Method.POST);
    }

    public Response getPetDetails () throws JsonProcessingException {
        String accessToken = userActions.getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);

        String payload = "{\n" +
                "  \"name\":" + "\"" + faker.dog().name() + "\"," + "\n" +
                "  \"sex\":" + "\"" + generateRandomPetGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + userActions.generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"about\":" + "\"" + faker.shakespeare().asYouLikeItQuote() + "\"," + "\n" +
                "  \"color\":" + "\"" + generatePetColor() + "\"," + "\n" +
                "  \"categoryId\":" + "" + generateRandomCategoryID() + "," + "\n" +
                "  \"breedId\":" + "" + generateRandomBreedID() + "\n" +
                "}";

        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),CreateNewPetResponse.class);
        int petID = 0;
        petID = createNewPetResponse.getId();
        return response = requestSpecification.request(Method.GET,"/"+petID);
    }


    public Response deletePet() throws JsonProcessingException {
        String accessToken = userActions.getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);

        String payload = "{\n" +
                "  \"name\":" + "\"" + faker.dog().name() + "\"," + "\n" +
                "  \"sex\":" + "\"" + generateRandomPetGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + userActions.generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"about\":" + "\"" + faker.shakespeare().asYouLikeItQuote() + "\"," + "\n" +
                "  \"color\":" + "\"" + generatePetColor() + "\"," + "\n" +
                "  \"categoryId\":" + "" + generateRandomCategoryID() + "," + "\n" +
                "  \"breedId\":" + "" + generateRandomBreedID() + "\n" +
                "}";

        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),CreateNewPetResponse.class);
        int petID = 0;
        petID = createNewPetResponse.getId();
        return response = requestSpecification.request(Method.DELETE,"/"+petID);
    }

    public Response uploadPetPhoto() throws IOException {
        String accessToken = userActions.getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);

        String payload = "{\n" +
                "  \"name\":" + "\"" + faker.dog().name() + "\"," + "\n" +
                "  \"sex\":" + "\"" + generateRandomPetGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + userActions.generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"about\":" + "\"" + faker.shakespeare().asYouLikeItQuote() + "\"," + "\n" +
                "  \"color\":" + "\"" + generatePetColor() + "\"," + "\n" +
                "  \"categoryId\":" + "" + generateRandomCategoryID() + "," + "\n" +
                "  \"breedId\":" + "" + generateRandomBreedID() + "\n" +
                "}";

        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),CreateNewPetResponse.class);

        File file = new File("src/main/resources/photos/tif.tif");
        byte[] bytes = Files.readAllBytes(file.toPath());
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photos", "tif.tif", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response deletePetPhoto() throws IOException {
        String accessToken = userActions.getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", "Bearer " + accessToken);

        String payload = "{\n" +
                "  \"name\":" + "\"" + faker.dog().name() + "\"," + "\n" +
                "  \"sex\":" + "\"" + generateRandomPetGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + userActions.generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"about\":" + "\"" + faker.shakespeare().asYouLikeItQuote() + "\"," + "\n" +
                "  \"color\":" + "\"" + generatePetColor() + "\"," + "\n" +
                "  \"categoryId\":" + "" + generateRandomCategoryID() + "," + "\n" +
                "  \"breedId\":" + "" + generateRandomBreedID() + "\n" +
                "}";

        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST);
        CreateNewPetResponse createNewPetResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),CreateNewPetResponse.class);

        File file = new File("src/main/resources/photos/tif.tif");
        byte[] bytes = Files.readAllBytes(file.toPath());
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/pet/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photos", "tif.tif", bytes);
        response = requestSpecification.request(Method.POST);

        Photos[] photos = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),Photos[].class);
        return response =requestSpecification.request(Method.DELETE,"/"+ Arrays.stream(photos).findFirst().get().getId());
    }







    public String generateRandomPetGender() {
        String[] arr = {"Male", "Female"};
        Random rand = new Random();
        int randomIndex = rand.nextInt(arr.length);
        String randomValue = arr[randomIndex];
        return randomValue;
    }

    public int generateRandomCategoryID() {
        Random random = new Random();
        return random.nextInt(11) + 1;
    }

    public int generateRandomBreedID() {
        Random random = new Random();
        return random.nextInt(11) + 1;
    }

    public String generatePetColor() {
        String[] arr = {"White", "Black","Brown"};
        Random rand = new Random();
        int randomIndex = rand.nextInt(arr.length);
        String randomValue = arr[randomIndex];
        return randomValue;
    }
}
