package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static backend.utils.Globals.RESPONSE_OK;
import static backend.utils.Globals.URL_BASE;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

public class StatusSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusSteps.class);

    private ValidatableResponse availablePets;

    @When("the user wants to know all the {string} pets")
    public void theUserWantsToKnowAllThePets(String status) {
        availablePets = when().get(String.format("%s/findByStatus?status=%s", URL_BASE, status))
                .then().statusCode(RESPONSE_OK);
    }

    @Then("all the returned pets should be {string}")
    public void allTheReturnedPetsShouldBe(String status) {
        availablePets.assertThat().body("status", everyItem(equalTo(status)));
    }

    @When("the user updates the pet status to {string}")
    public void theUserUpdatesThePetStatusTo(String status) {
        given().contentType(ContentType.URLENC)
                .formParam("status", status)
                .when().post(String.format("%s/%s", URL_BASE, Pet.id))
                .then().statusCode(RESPONSE_OK);
        Pet.status = status;
    }

    @Then("the pet status is {string}")
    public void thePetStatusIs(String status) {
        String receivedStatus = when().get(String.format("%s/%s", URL_BASE, Pet.id))
                .then().statusCode(RESPONSE_OK)
                .extract().path("status");
        assertThat(receivedStatus).isEqualTo(status);
    }
}
