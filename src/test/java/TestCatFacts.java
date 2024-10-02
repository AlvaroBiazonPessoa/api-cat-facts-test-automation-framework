import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

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
        String emptyBreedList = "";
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("data", not(equalTo(emptyBreedList)));
    }

    @Test
    @DisplayName("Get list of breeds with a valid limit")
    public void getListOfBreedsWithAValidLimit() {
        int validLimit = 2;
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, validLimit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Get empty list of breeds")
    public void getEmptyListOfBreeds() {
        int limit = 2;
        given()
            .header(VALID_AUTHENTICATION_TOKEN_NAME, VALID_AUTHENTICATION_TOKEN_VALUE)
            .queryParam(QUERY_PARAMETER_LIMIT, limit)
        .when()
            .get(URL_API_CAT_FACTS + ENDPOINT_BREEDS)
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

}