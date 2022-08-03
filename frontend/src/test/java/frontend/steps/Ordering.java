package frontend.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static frontend.utils.Delay.delay;
import static org.assertj.core.api.Assertions.assertThat;

public class Ordering {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);

    private ChromeDriver driver;
    private Map<String, String> variables = new HashMap<>();

    @Given("I visit Demoblaze")
    public void i_visit_demoblaze() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://www.demoblaze.com");
        driver.manage().window().maximize();
    }

    @When("navigate to category {string}")
    public void navigateToCategory(String category) {
        driver.findElement(By.xpath(
                String.format("//*[@id=\"itemc\" " +
                        "and contains(@onclick,'byCat') a" +
                        "nd contains(text(),\"%s\")]", category))).click();

        delay(700);
    }

    @When("navigate to menu {string}")
    public void navigateToMenu(String menu) {
        driver.findElement(By.xpath(
                String.format("//*[@id=\"navbarExample\"]/ul" +
                        "//a[contains(text(),\"%s\")]", menu))).click();
        
        delay(700);
    }

    @When("click on {string}")
    public void clickOn(String textLink) {
        driver.findElement(By.xpath(
                String.format("//a[text()=\"%s\"] | " +
                        "//button[text()=\"%s\"]", textLink, textLink))).click();
    }

    @And("accept pop up {string}")
    public void acceptPopUp(String accept) {
        boolean value = Boolean.valueOf(accept);
        try {
            FluentWait wait = new FluentWait(driver);
            wait.until(ExpectedConditions.alertIsPresent());
            try {
                Alert alert = driver.switchTo().alert();
                if (value) {
                    alert.accept();
                } else {
                    alert.dismiss();
                }
            } catch (NoAlertPresentException e) {
                LOGGER.error("An exceptional case");
            }
        } catch (TimeoutException e) {
            LOGGER.error("WebDriver couldn’t locate the Alert");
        }
    }

    @And("select product {string}")
    public void selectProduct(String product) {
        driver.findElement(By.xpath(
                String.format("//*[@id=\"tbodyid\"]//a[text()=\"%s\"]", product))).click();
    }

    @And("delete {string} from the cart")
    public void deleteFromTheCart(String product) {
        By elementPath = By.xpath(
                String.format("//*[@id=\"tbodyid\"]//td[contains(text(),\"%s\")]" +
                        "//ancestor::tr" +
                        "//td/a[contains(@onclick, 'deleteItem')]", product));
        driver.findElement(elementPath).click();

        FluentWait wait = new FluentWait(driver);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementPath));
    }

    @And("fill the fields")
    public void fillTheFields(DataTable fields) {
        Map<String, String> rows = fields.asMaps(String.class, String.class).get(0);

        for (Map.Entry<String, String> data : rows.entrySet()) {
            driver.findElement(By.xpath(
                            String.format("//input[@id=\"%s\"]", data.getKey())))
                    .sendKeys(data.getValue());
        }
    }

    @Then("amount expected is correct")
    public void amountExpectedIsCorrect() {
        assertThat(variables.get("Amount").split("\\s")[0].trim())
                .isEqualTo(variables.get("expectedAmount"));
    }

    @And("close page")
    public void closePage() {
        driver.quit();
    }


    @And("capture total amount from the cart")
    public void captureTotalAmountFromTheCart() {
        String expectedAmount = driver.findElement(By.xpath("//*[@id=\"totalp\"]")).getText();
        variables.put("expectedAmount", expectedAmount.trim());

        LOGGER.info(String.format("[EXPECTED] Amount: %s", expectedAmount));
    }

    @And("capture info from purchase summary")
    public void captureInfoFromPurchase() {
        String result = driver.findElement(By.xpath(
                "/html/body/div[10]/p")).getText();

        for (String line : result.split("\n")) {
            String[] info = line.split(":");
            variables.put(info[0].trim(), info[1].trim());
        }
    }

    @And("log {string}")
    public void log(String nameVar) {
        String valueVar = variables.get(nameVar);

        LOGGER.info(String.format("[CAPTURE] %s: %s", nameVar, valueVar));
    }
}
