package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static backend.utils.Globals.RESPONSE_OK;
import static backend.utils.Globals.URL_BASE;
import static io.restassured.RestAssured.when;

public class DeletePetSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePetSteps.class);

    @When("the user deletes pet")
    public void theUserDeletesPet() {
        when().delete(String.format("%s/%s", URL_BASE, Pet.id))
                .then().statusCode(RESPONSE_OK);
    }
}
