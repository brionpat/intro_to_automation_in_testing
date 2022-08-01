package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static backend.utils.Globals.RESPONSE_OK;
import static backend.utils.Globals.URL_BASE;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AddPetSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddPetSteps.class);

    private String petName;

    @When("the user adds a new pet with {string} name, {string}, and {string} status")
    public void the_user_adds_a_new_pet_with_name_and_status(String name, String category, String status) {
        Pet.name = given().contentType(ContentType.JSON)
                .body(makePet(name, category, status))
                .when().post(String.format("%s", URL_BASE))
                .then().statusCode(RESPONSE_OK)
                .extract().path("name");
    }

    private String makePet(String name, String category, String status) {
        Pet.id = String.valueOf(Math.abs(new Random().nextInt()));
        LOGGER.info(String.format("Pet Id added: %s", Pet.id));
        Pet.categoryId = String.valueOf(Math.abs(new Random().nextInt()));
        Pet.name = petName = name;
        Pet.categoryName = category;
        Pet.status = status;

        return String.format(
                "{\n" +
                        "  \"id\": %s,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": %s,\n" +
                        "    \"name\": \"%s\"\n" +
                        "  },\n" +
                        "  \"name\": \"%s\",\n" +
                        "  \"photoUrls\": [],\n" +
                        "  \"tags\": [],\n" +
                        "  \"status\": \"%s\"\n" +
                        "}",
                Pet.id,
                Pet.categoryId,
                Pet.categoryName,
                Pet.name,
                Pet.status
        );
    }


    @Then("the pet name is {string}")
    public void thePetNameIs(String petName) {
        assertThat(petName).isEqualTo(petName);
    }
}
