package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.furbaby.pojo.user.*;
import com.furbaby.utils.ObjectConverter;
import io.qameta.allure.Description;
import io.restassured.http.Method;

import static org.testng.Assert.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;


public class UserTests extends BaseApiTest {

    String expectedPhoneNumber = null;
    String phoneNumber = "";
    String phoneNumberForGetUser = "";

    int userID = 0;


    @Test
    @Description("Create new user positive")
    public void createNewUser() throws JsonProcessingException {
        expectedPhoneNumber = userActions.generateAustralianRandomPhoneNumber();
        response = userActions.createNewUser(expectedPhoneNumber);
        UserLogin userLogin = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), UserLogin.class);
        assertEquals(response.getStatusCode(), 200);
        assertEquals(userLogin.getPhoneNumber(), expectedPhoneNumber);
        phoneNumber = userLogin.getPhoneNumber();
        userID = userLogin.getId();
        phoneNumberForGetUser = userLogin.getPhoneNumber();
    }

    @Test(dependsOnMethods = "createNewUser")
    @Description("Create Existing User with phone number")
    public void CreateExistingUser() throws JsonProcessingException {
        response = userActions.createNewUser(expectedPhoneNumber);
        UserLogin userLogin = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), UserLogin.class);
        userID = userLogin.getId();
        assertEquals(response.getStatusCode(), 201);
        assertEquals(userLogin.getPhoneNumber(), expectedPhoneNumber);
        assertEquals(userLogin.getId(), userID);
    }

    @Test
    @Description("Create user with invalid phone number")
    public void createUserWithInvalidPhoneNumberTest() {
        response = userActions.createNewUser(userActions.generateInvalidPhoneNumber());
        JsonPath jsonPath = response.jsonPath();
        String invalidPhoneErrMess = jsonPath.getString("detail.phoneNumber");
        assertEquals(response.getStatusCode(), 422);
        assertTrue(response.body().asString().contains(invalidPhoneErrMess));
    }

    @Test
    @Description("Create new user with Null phone number")
    public void createUserWithNoPhoneNumber() {
        response = userActions.createNewUser(null);
//        JsonPath jsonPath = response.jsonPath();
//        String invalidPhoneErrMess = jsonPath.getString("detail.phoneNumber");
        assertEquals(response.getStatusCode(), 422);
//        assertTrue(response.body().asString().contains(invalidPhoneErrMess));
    }

    @Test(dependsOnMethods = "createNewUser")
    @Description("OTP Send Test")
    public void otpSendTest() throws JsonProcessingException {
        response = userActions.sendOTP(expectedPhoneNumber);
        OtpSendResponse actualOtpSendMessage = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), OtpSendResponse.class);
        assertEquals(actualOtpSendMessage.getDetail(), "OTP code was created, but not sent");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Send OTP code for non existing user")
    public void sendOtpForNonExistingUserTest() throws JsonProcessingException {
        response = userActions.sendOTP(null);
        OtpSendResponse actualOtpSendMessage = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), OtpSendResponse.class);
        assertEquals(actualOtpSendMessage.getDetail(), "User doesn't exist");
        assertEquals(response.getStatusCode(), 404);

        response = userActions.sendOTP(userActions.generateInvalidPhoneNumber());
        OtpSendResponse actualOtpSendMessage1 = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), OtpSendResponse.class);
        assertEquals(actualOtpSendMessage1.getDetail(), "User doesn't exist");
        assertEquals(response.getStatusCode(), 404);
    }


    @Test(dependsOnMethods = {"createNewUser", "otpSendTest"})
    @Description("Verify OTP code")
    public void verifyOtpCodeTest() throws JsonProcessingException {

        response = userActions.verifyOtpCode(expectedPhoneNumber);
        OtpVerifyResponse actualOtpVerify = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), OtpVerifyResponse.class);
        assertEquals(response.getStatusCode(), 200);
        accessToken = actualOtpVerify.getAccessToken();
    }


    @Test(dependsOnMethods = {"createNewUser", "otpSendTest", "verifyOtpCodeTest"})
    @Description("Verify OTP error message")
    public void verifyOtpErrorMessage() {
        requestSpecification.contentType(headers.acceptJson());
        requestSpecification.accept(headers.acceptJson());
        String payload = "{\n" +
                "  \"phoneNumber\":" + "\"" + phoneNumber + "\"," + "\n" +
                "  \"otp\": \"1234\"\n" +
                "}";
        requestSpecification.body(payload);
        response = requestSpecification.request(Method.POST, "/otp/verify");
        JsonPath jsonPath = response.jsonPath();
        assertEquals(response.getStatusCode(), 422);
        String otpErrMessage = jsonPath.getString("detail.otp");
        assertTrue(response.body().prettyPrint().contains(otpErrMessage));
    }

    @Test(dependsOnMethods = {"createNewUser", "otpSendTest", "verifyOtpCodeTest"})
    @Description("GET user details")
    public void updateUserDetails() {
        response = userActions.getUserDetails(accessToken);
        JsonPath jsonPath = response.jsonPath();
        assertEquals(response.getStatusCode(), 200);
        assertEquals(jsonPath.getString("phoneNumber"), phoneNumberForGetUser);
    }

    @Test()
    @Description("Verify user details are editable")
    public void updateUserDetailsTest() throws JsonProcessingException {
        response = userActions.updateUserDetails(userActions.getUserAccessToken());
        UserLogin user = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(), UserLogin.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(user.getLastName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getGender());
        assertNotNull(user.getEmail());

    }

    @Test
    @Description("Verify that user can be deleted")
    public void deleteUserTest() throws JsonProcessingException {
        response = userActions.deleteUser();
        UserDelete user = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserDelete.class);
        assertEquals(response.getStatusCode(),200);
        assertTrue(user.getDetail().contains("was deleted"));
    }

    @Test
    @Description("Verify that user can upload jpeg photo")
    public void uploadJpegPhotoToUserTest() throws Exception {
        response = userActions.uploadJpegPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify that user can upload jpg photo")
    public void uploadJpgPhotoToUserTest() throws Exception {
        response = userActions.uploadJpgPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify that user can upload gif photo")
    public void uploadGifPhotoToUserTest() throws Exception {
        response = userActions.uploadGifPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify that user can upload heic photo")
    public void uploadHeicPhotoToUserTest() throws Exception {
        response = userActions.uploadHeicPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify that user can upload PNG photo")
    public void uploadPngPhotoToUserTest() throws Exception {
        response = userActions.uploadPngPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify that user can upload Tif photo")
    public void uploadTifPhotoToUserTest() throws Exception {
        response = userActions.uploadTifPhoto();
        UserUploadPhotoResponse uploadPhotoResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserUploadPhotoResponse.class);
        assertEquals(response.getStatusCode(),200);
        assertNotNull(uploadPhotoResponse.getName());
        assertNotNull(uploadPhotoResponse.getUrl());
    }

    @Test
    @Description("Verify user can delete photo")
    public void deleteUsersPhotoTest() throws IOException {
        response = userActions.deleteUsersPhoto();
        UserDelete userDelete = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserDelete.class);
        assertEquals(response.getStatusCode(),200);
        assertTrue(userDelete.getDetail().contains("was deleted"));
    }

    @Test
    @Description("Verify user can change the phone number")
    public void changePhoneNumberTest() throws IOException {
        response = userActions.changePhoneNumber();
        assertEquals(response.getStatusCode(),200);
    }




}
