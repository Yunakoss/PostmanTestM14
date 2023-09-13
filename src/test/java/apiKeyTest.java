
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class apiKeyTest {
    private static Response response;

    @BeforeAll
    public static void setup() {
        response = given().get(Consts.URL + Consts.YOUR_ACCESS_KEY);
        System.out.println(response.asString());
    }

    @Test
    public void validApiKeyTest() {
        response.then().statusCode(200);
    }

    @Test
    public void invalidApiKeyTest() {
        Response response = given().auth().oauth2("OfXynl0B3Y3sH4XJGFqW3thmu13q6A9").contentType("application/json").get(Consts.URL + Consts.YOUR_ACCESS_KEY);
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("Invalid"));
    }

    @Test
    public void getQuotesTest() {
        Response response = given().auth().oauth2("OfXynl0B3Y3sH4XJGFqW3thmu13q6A94").contentType("application/json").get(Consts.URL + Consts.YOUR_ACCESS_KEY);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes.USDAUD", notNullValue());
        response.then().body("quotes.USDAED", notNullValue());
        response.then().body("quotes.USDANG", notNullValue());
    }
    @Test
    public void negativeEndPointTest(){
        Response response = given().contentType("application/json").get(Consts.WRONG_URL + Consts.YOUR_ACCESS_KEY);
        System.out.println(response.asString());
        response.then().statusCode(404);
        response.then().body("message", containsString("no Route"));
    }
    @Test
    public void noAccessKeyTest(){
        Response response = given().contentType("application/json").get(Consts.WRONG_URL + " ");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("No API"));
    }
    @Test
    public void getHistoricalQuotesTest() {
        Response response = given().contentType("application/json").get(Consts.HISTORICAL_URL + Consts.YOUR_ACCESS_KEY);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes.USDAUD", equalTo((float)1.281703)); //converts integer to float
    }
}
