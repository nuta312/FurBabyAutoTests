package tests;

import com.furbaby.common.PetActions;
import com.furbaby.common.UserActions;
import com.furbaby.common.Headers;
import com.furbaby.pojo.user.UserLogin;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {
    UserActions userActions;
    PetActions petActions;
    Headers headers;
    RequestSpecification requestSpecification;
    Response response;
    UserLogin userLogin;


    public  String accessToken;

    @BeforeClass
    public void setUp(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://furbaby-back-dev.appelloproject.xyz/api/v1");

        userActions = new UserActions();
        headers = new Headers();
        userLogin = new UserLogin();
        petActions = new PetActions();
        accessToken = "";
    }
}
