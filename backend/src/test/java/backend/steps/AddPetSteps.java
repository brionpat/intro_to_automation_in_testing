package backend.steps;

import backend.utils.Pet;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AddPetSteps {

    private Pet pet;

    public AddPetSteps(Pet pet) {
        this.pet = pet;
    }

    @When("the user adds a new pet with {string} name, {string}, and {string} status")
    public void the_user_adds_a_new_pet_with_name_and_status(String name, String category, String status) {
        pet.name = given().contentType(ContentType.JSON)
                .body(makePet(name, category, status))
                .when().post("https://petstore.swagger.io/v2/pet")
                .then().statusCode(200)
                .extract().path("name");
    }

    private String makePet(String name, String category, String status) {
        pet.id = String.valueOf(Math.abs(new Random().nextInt()));
        pet.categoryId = String.valueOf(Math.abs(new Random().nextInt()));
        pet.name = name;
        pet.categoryName = category;
        pet.status = status;

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
                pet.id,
                pet.categoryId,
                pet.categoryName,
                pet.name,
                pet.status
        );
    }


    @Then("the pet name is {string}")
    public void thePetNameIs(String petName) {
        assertThat(petName).isEqualTo(this.pet.name);
    }
}
