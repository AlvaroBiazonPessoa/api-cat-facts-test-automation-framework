import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

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
}