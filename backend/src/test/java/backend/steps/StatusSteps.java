package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class StatusSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(StatusSteps.class);

    private ValidatableResponse availablePets;
    private Pet pet;

    public StatusSteps(Pet pet) {
        this.pet = pet;
    }

    @When("the user wants to know all the {string} pets")
    public void theUserWantsToKnowAllThePets(String status) {
        availablePets = when().get(String.format("https://petstore.swagger.io/v2/pet/findByStatus?status=%s", status))
                .then().statusCode(200);
    }

    @Then("all the returned pets should be {string}")
    public void allTheReturnedPetsShouldBe(String status) {
        availablePets.assertThat().body("status", Matchers.everyItem(equalTo(status)));
    }

    @Given("the pet with {string} name, {string}, and {string} status")
    public void thePetWithNameAndStatus(String arg0, String arg1, String arg2) {
    }

    @When("the user updates the pet status to {string}")
    public void theUserUpdatesThePetStatusTo(String status) {

    }

    @Then("the pet status is {string}")
    public void thePetStatusIs(String status) {
    }
}
