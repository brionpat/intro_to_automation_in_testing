package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static backend.utils.Globals.*;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

public class InventorySteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventorySteps.class);

    @Then("the pet is in the inventory {string}")
    public void thePetIsInTheInventory(String value) {
        boolean exists = Boolean.parseBoolean(value);
        Integer expectedStatusCode = exists ? RESPONSE_OK : RESPONSE_NOT_FOUND;

        Integer statusCode = when().post(String.format("%s/%s", URL_BASE, Pet.id))
                .then().extract().statusCode();

        assertThat(statusCode).isEqualTo(expectedStatusCode);
    }
}
