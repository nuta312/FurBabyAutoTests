package com.furbaby.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.furbaby.pojo.user.OtpVerifyResponse;
import com.furbaby.utils.ObjectConverter;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UserActions {
    Random rand = new Random();
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecification1;
    Response response;
    Faker faker = new Faker();

    public String generateAustralianRandomPhoneNumber() {
        int num1 = rand.nextInt(7) + 2; // generates a number between 2 and 8
        int num2 = rand.nextInt(898) + 100; // generates a number between 100 and 998
        int num3 = rand.nextInt(8999) + 1000; // generates a number between 1000 and 9999
        return "+61" + num1 + num2 + num3;
    }

    public String generateInvalidPhoneNumber() {
        int num2 = rand.nextInt(898) + 100; // generates a number between 100 and 998
        int num3 = rand.nextInt(8999) + 1000; // generates a number between 1000 and 9999
        return "+61" + num2 + num3;
    }


    public Response createNewUser(String phoneNumber) {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/login");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        String payload = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"" + "\n" +
                "}";
        requestSpecification.body(payload);
        return response = requestSpecification.request(Method.POST);
    }

    public Response sendOTP(String phoneNumber) {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/otp/send");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        String payload = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"" + "\n" +
                "}";
        requestSpecification.body(payload);
        return response = requestSpecification.request(Method.POST);
    }

    public Response verifyOtpCode(String phoneNumber) {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/otp/verify");
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.contentType(ContentType.JSON);
        String payload = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"," + "\n" +
                "  \"otp\": \"1111\"\n" +
                "}";
        requestSpecification.body(payload);
        return response = requestSpecification.request(Method.POST);
    }


    public Response getUserDetails(String accessToken) {
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        return response = requestSpecification.request(Method.GET);
    }

    public Response updateUserDetails(String accessToken) {
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        String payload = "{\n" +
                "  \"isNew\": true,\n" +
                "  \"firstName\":" + "\"" + faker.name().firstName() + "\"," + "\n" +
                "  \"lastName\":" + "\"" + faker.name().lastName() + "\"," + "\n" +
                "  \"email\":" + "\"" + faker.internet().emailAddress() + "\"," + "\n" +
                "  \"gender\":" + "\"" + generateRandomGender() + "\"," + "\n" +
                "  \"dateOfBirth\":" + "\"" + generateRandomDateOfBirth() + "\"," + "\n" +
                "  \"postcode\":" + "\"" + generateRandomZipCode() + "\"," + "\n" +
                "  \"isActive\": true\n" +
                "}";
        requestSpecification.body(payload);
        return response = requestSpecification.request(Method.PATCH);
    }

    public String getUserAccessToken() throws JsonProcessingException {
        String accessToken = "";
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        response = verifyOtpCode(phoneNumber);
        OtpVerifyResponse actualOtpVerify = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), OtpVerifyResponse.class);
        accessToken = actualOtpVerify.getAccessToken();
        return accessToken;
    }

    public Response deleteUser() throws JsonProcessingException {
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        return response = requestSpecification.request(Method.DELETE);
    }

    public Response uploadJpegPhoto() throws IOException {
        File jpeg = new File("src/main/resources/photos/jpeg.jpeg");
        byte[] bytes = Files.readAllBytes(jpeg.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "jpeg.jpeg", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response uploadJpgPhoto() throws IOException {
        File jpg = new File("src/main/resources/photos/JPEG.jpg");
        byte[] bytes = Files.readAllBytes(jpg.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "JPEG.jpg", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response uploadGifPhoto() throws IOException {
        File file = new File("src/main/resources/photos/gif.gif");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "gif.gif", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response uploadHeicPhoto() throws IOException {
        File file = new File("src/main/resources/photos/photo1675246797.heic");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "photo1675246797.heic", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response uploadPngPhoto() throws IOException {
        File file = new File("src/main/resources/photos/PNG.png");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "PNG.png", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response uploadTifPhoto() throws IOException {
        File file = new File("src/main/resources/photos/tif.tif");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "tif.tif", bytes);
        return response = requestSpecification.request(Method.POST);
    }

    public Response deleteUsersPhoto() throws IOException {
        File jpeg = new File("src/main/resources/photos/jpeg.jpeg");
        byte[] bytes = Files.readAllBytes(jpeg.toPath());
        String phoneNumber = generateAustralianRandomPhoneNumber();
        createNewUser(phoneNumber);
        sendOTP(phoneNumber);
        verifyOtpCode(phoneNumber);
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/photo");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        requestSpecification.multiPart("photo", "jpeg.jpeg", bytes);
        response = requestSpecification.request(Method.POST);
        Assert.assertEquals(response.getStatusCode(),200);
        return response = requestSpecification.request(Method.DELETE);
    }

    public Response changePhoneNumber() throws JsonProcessingException {
        String phoneNumber = generateAustralianRandomPhoneNumber();
        String accessToken = getUserAccessToken();
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/otp/send");
        requestSpecification.header("Authorization", "Bearer " + accessToken);
        String payload = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"" + "\n" +
                "}";
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        requestSpecification.body(payload);
        requestSpecification.request(Method.POST);

        requestSpecification1 = RestAssured.given();
        requestSpecification1.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1/user/me/phone_number");
        requestSpecification1.header("Authorization", "Bearer " + accessToken);
        String payload1 = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"," + "\n" +
                "  \"otp\": \"1111\"\n" +
                "}";
        requestSpecification1.contentType(ContentType.JSON);
        requestSpecification1.accept(ContentType.JSON);
        requestSpecification1.body(payload1);
        return response = requestSpecification1.request(Method.PATCH);
    }

    public String getNewAccessTokenAfterChangeNumber() throws JsonProcessingException {
        response = changePhoneNumber();
        OtpVerifyResponse otpVerifyResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),OtpVerifyResponse.class);
        return otpVerifyResponse.getAccessToken();
    }


    public String generateRandomZipCode() {
        Random rand = new Random();
        int n = rand.nextInt(8999) + 1000;
        return String.valueOf(n);
    }


    public String generateRandomGender() {
        String[] arr = {"Male", "Female", "Non-Binary", "Prefer Not To Say"};
        Random rand = new Random();
        int randomIndex = rand.nextInt(arr.length);
        String randomValue = arr[randomIndex];
        return randomValue;
    }

    public int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public String generateRandomDateOfBirth() {
        Calendar cal = Calendar.getInstance();
        int year = randBetween(1900, cal.get(Calendar.YEAR));
        cal.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }


}
