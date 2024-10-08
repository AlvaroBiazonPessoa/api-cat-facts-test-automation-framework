import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCatFacts {

    private static final String URL_API_CAT_FACTS = "https://catfact.ninja/";
    private static final String ENDPOINT_BREEDS = "breeds";
    private static final String VALID_AUTHENTICATION_TOKEN_NAME = "X-CSRF-TOKEN";
    private static final String VALID_AUTHENTICATION_TOKEN_VALUE = "KqTRMzyCERr6njfNmmI2fGpW5otfuvL0IG1RB4th";
    private static final String QUERY_PARAMETER_LIMIT = "limit";

    @Test
    @DisplayName("Get list of breeds without authentication")
    public void getListOfBreedsWithoutAuthentication() {
        given()
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Get list of breeds by providing invalid authentication credentials")
    public void getListOfBreedsByProvidingInvalidAuthenticationCredentials() {
        String invalidAuthenticationTokenName = "X-CSRF-TOKEN-FALSE";
        String invalidAuthenticationTokenNameValue = "KqTRMzyCERr6njfNmmI2fGpW5otfuvL0IG1RB4th1008";
        given()
            .header(invalidAuthenticationTokenName, invalidAuthenticationTokenNameValue)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Get list of breeds by providing valid authentication credentials")
    public void getListOfBreedsByProvidingValidAuthenticationCredentials() {
        int emptyListSize = 0;
        Response response = (Response) given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();
        String responseBody = response.getBody().asString();
        JSONArray dataList = JsonPath.read(responseBody, "$.data");
        int numberOfElementsInTheDataList = dataList.size();
        assertNotEquals(emptyListSize, numberOfElementsInTheDataList);
    }

    @Test
    @DisplayName("Get list of breeds with a valid limit")
    public void getListOfBreedsWithAValidLimit() {
        int validLimit = 2;
        Response response = (Response) given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, validLimit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();
        String responseBody = response.getBody().asString();
        JSONArray dataList = JsonPath.read(responseBody, "$.data");
        int numberOfElementsInTheDataList = dataList.size();
        assertEquals(validLimit, numberOfElementsInTheDataList);
    }

    @Test
    @DisplayName("Get empty list of breeds")
    public void getEmptyListOfBreeds() {
        int limit = 0;
        Response response = (Response) given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, limit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();
        String responseBody = response.getBody().asString();
        JSONArray dataList = JsonPath.read(responseBody, "$.data");
        int numberOfElementsInTheDataList = dataList.size();
        assertEquals(limit, numberOfElementsInTheDataList);
    }

    @Test
    @DisplayName("Get list of breeds with an invalid limit")
    public void getListOfBreedsWithAnInvalidLimit() {
        String invalidLimit = "abc";
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, invalidLimit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Get list of breeds with a negative limit value")
    public void getListOfBreedsWithANegativeLimitValue() {
        int negativeLimit = -2;
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, negativeLimit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Get list of breeds with the invalid limit parameter name")
    public void getListOfBreedsWithTheInvalidLimitParameterName() {
        String queryParameterLiimmiitt = "liimmiitt";
        int liimmiitt = 2;
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(queryParameterLiimmiitt, liimmiitt)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Get list of breeds with the invalid endpoint name")
    public void getListOfBreedsWithTheInvalidEndpointName() {
        String endpointBrreedss = "brreedss";
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
        .when()
            .get(URL_API_CAT_FACTS + endpointBrreedss)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}