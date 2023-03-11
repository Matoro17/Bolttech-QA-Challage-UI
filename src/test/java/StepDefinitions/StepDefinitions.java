package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import io.github.sukgu.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.html.HTMLFieldSetElement;

public class StepDefinitions {
    ChromeDriver driver;

    WebElement shadowSelector(String selector){
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> {
            Shadow shadow = new Shadow(driver);

            WebElement e = shadow.findElement(selector);
            return e;
        });
        return element;
    }

    @After
    public void close(){
        driver.close();
    }

    @Given("I access Bolttech in mobile view")
    public void iAccessBolttech() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 6");
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--remote-allow-origins=*");
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.bolttech.co.th/en/ptgroup/device-protection?utm_source=ptgroup&utm_customer=123123123123132");

        shadowSelector("#onetrust-close-btn-container > button").click();

    }

    @When("The price range input loads")
    public void thePriceRangeInputLoads() throws InterruptedException {

        WebElement t = shadowSelector("#selected");
        t.click();

    }

    @Then("It should be possible to retrieve it's options")
    public void itShouldBePossibleToRetrieveItSOptions() {
        WebElement field = shadowSelector("fieldset > div > div > div > ul > li:nth-child(2)");
        WebElement field2 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(3)");
        WebElement field3 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(4)");
        WebElement field4 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(5)");
        WebElement field5 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(6)");
        WebElement field6 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(7)");
        WebElement field7 = shadowSelector("fieldset > div > div > div > ul > li:nth-child(8)");

        Assert.assertEquals("THB 2,000 - 6,000", field.getAttribute("innerHTML"));
        Assert.assertEquals("THB 6,001 - 10,000", field2.getAttribute("innerHTML"));
        Assert.assertEquals("THB 10,001 - 15,000", field3.getAttribute("innerHTML"));
        Assert.assertEquals("THB 15,001 - 22,000", field4.getAttribute("innerHTML"));
        Assert.assertEquals("THB 22,001 - 26,000", field5.getAttribute("innerHTML"));
        Assert.assertEquals("THB 26,001 - 36,000", field6.getAttribute("innerHTML"));
        Assert.assertEquals("THB 36,001 - 65,000", field7.getAttribute("innerHTML"));
    }

    @And("I select from the price range selector {string}")
    public void iSelectFromThePriceRangeSelector(String range) throws InterruptedException {
        WebElement rangeSelectorList = shadowSelector("fieldset > div > div > div > ul");
        List<WebElement> allChildElements = rangeSelectorList.findElements(By.xpath("li"));
        for (int i = 0; i < allChildElements.size(); i++){
            String price = allChildElements.get(i).getText();
            if (Objects.equals(price, "")){
                price = allChildElements.get(i).getAttribute("innerHTML");
            }
            if (Objects.equals(price, range)){
                Thread.sleep(2000);
                allChildElements.get(i).click();
                Thread.sleep(2000);
                break;
            }
            if (i == allChildElements.size()){
                throw new Error("Range not found");
            }
        }
    }

    @Then("The price should be {string}")
    public void thePriceShouldBe(String price) throws InterruptedException {

        WebElement priceElement = shadowSelector("#dynamicPrice");

        Assert.assertEquals(price+".00", priceElement.getText());
    }

    @Then("I click on Select")
    public void iClickOnSelect() throws InterruptedException {
        shadowSelector("edi-card-vertical-content > div > edi-cta >div > a").click();
        Thread.sleep(2000);

    }

    @When("The details page loads")
    public void theDetailsPageLoads() {
        Assert.assertEquals(shadowSelector("#step1 > edi-product-summary > div > b").getText(),"Great choice,");
    }

    @Then("The striked Through Price should be {string}")
    public void theStrikedThroughPriceShouldBe(String price) {
        WebElement priceSelector = shadowSelector(" .final-price");
        Assert.assertEquals(price+".00", priceSelector.getText());
    }

    @And("Provider should be {string}")
    public void providerShouldBe(String bolttech) {
        Assert.assertEquals(shadowSelector("#providerName").getText(), bolttech);
    }

    @And("The contract start date should be Today in Thailand Timezone")
    public void theContractStartDateShouldBeTodayInThailandTimezone() throws ParseException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd MMM YYYY", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        String strDate = df.format(date);
        String strDateNoDot = strDate.replace(".","");

        WebElement startDate = shadowSelector("#subscriptionStartDate");

        Assert.assertEquals(strDateNoDot.toLowerCase(), startDate.getText().toLowerCase());
    }

    @And("The contract renewal should be {string}")
    public void theContractRenewalShouldBe(String monthly) {
        Assert.assertEquals(shadowSelector("#subscriptionRenewal").getText(), monthly);
    }

    @And("The product name Should be {string}")
    public void theProductNameShouldBe(String insuranceName) {
        WebElement nameSelector = shadowSelector("#productSummary > div > edi-loading > div.panel-content.summary-title > p");

        Assert.assertEquals(nameSelector.getText(), insuranceName);
    }

    @And("The billing start date should be the start date plus {int} months minus {int} days")
    public void theBillingStartDateShouldBeTheStartDatePlusMonthsMinusDays(int monthSum, int minusDays) throws ParseException {

        Date billingDate = DateUtils.addMonths(new Date(), monthSum);
        billingDate = DateUtils.addDays(billingDate, minusDays * (-1));

        DateFormat df = new SimpleDateFormat("dd MMM YYYY", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        String strDate = df.format(billingDate);
        String strDateNoDot = strDate.replace(".","");

        WebElement billingDateSelector = shadowSelector("#billingStartDate");

        Assert.assertEquals(strDateNoDot.toLowerCase(), billingDateSelector.getText().toLowerCase());
    }
}
