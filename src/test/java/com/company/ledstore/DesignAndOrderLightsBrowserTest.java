package com.company.ledstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderLightsBrowserTest {

    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate rest;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void testDesignALightPage_HappyPath() throws Exception {
        browser.get(homePageUrl());
        clickDesignALight();
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        clickBuildAnotherLight();
        buildAndSubmitALight("Another Light", 4L, 5L, 6L);
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    @Test
    public void testDesignALightPage_EmptyOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignALight();
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    @Test
    public void testDesignALightPage_InvalidOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignALight();
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    //
    // Browser test action methods
    //
    private void buildAndSubmitALight(String name, Long... features) {
        assertDesignPageElements();

        for (Long feature : features) {
            browser.findElement(By.cssSelector("input[value='" + feature + "']")).click();
        }
        browser.findElement(By.cssSelector("input#name")).sendKeys(name);
        browser.findElement(By.cssSelector("form")).submit();
    }

    private void assertDesignPageElements() {
        assertThat(browser.getCurrentUrl()).isEqualTo(designPageUrl());
        List<WebElement> featureGroups = browser.findElements(By.className("feature-group"));
        assertThat(featureGroups.size()).isEqualTo(3);

        WebElement k3000Group = browser.findElement(By.cssSelector("div.feature-group#temperature3000"));
        List<WebElement> k3000s = k3000Group.findElements(By.tagName("div"));
        assertThat(k3000s.size()).isEqualTo(6);
        assertFeature(k3000Group, 0, "1", "10W 3000K 500mm WOOD");
        assertFeature(k3000Group, 1, "2", "20W 3000K 1000mm WOOD");
        assertFeature(k3000Group, 2, "3", "40W 3000K 2000mm WOOD");
        assertFeature(k3000Group, 3, "10", "10W 3000K 500mm ALUMINIUM");
        assertFeature(k3000Group, 4, "11", "20W 3000K 1000mm ALUMINIUM");
        assertFeature(k3000Group, 5, "12", "40W 3000K 2000mm ALUMINIUM");

        WebElement k4000Group = browser.findElement(By.cssSelector("div.feature-group#temperature4000"));
        List<WebElement> k4000s = k4000Group.findElements(By.tagName("div"));
        assertThat(k4000s.size()).isEqualTo(6);
        assertFeature(k4000Group, 0, "4", "10W 4000K 500mm ALUMINIUM");
        assertFeature(k4000Group, 1, "5", "20W 4000K 1000mm ALUMINIUM");
        assertFeature(k4000Group, 2, "6", "40W 4000K 2000mm ALUMINIUM");
        assertFeature(k4000Group, 3, "13", "10W 4000K 500mm WOOD");
        assertFeature(k4000Group, 4, "14", "20W 4000K 1000mm WOOD");
        assertFeature(k4000Group, 5, "15", "40W 4000K 2000mm WOOD");

        WebElement k5000Group = browser.findElement(By.cssSelector("div.feature-group#temperature5000"));
        List<WebElement> k5000s = k5000Group.findElements(By.tagName("div"));
        assertThat(k5000s.size()).isEqualTo(6);
        assertFeature(k5000Group, 0, "7", "10W 5000K 500mm WOOD");
        assertFeature(k5000Group, 1, "8", "20W 5000K 1000mm WOOD");
        assertFeature(k5000Group, 2, "9", "40W 5000K 2000mm WOOD");
        assertFeature(k5000Group, 3, "16", "10W 5000K 500mm ALUMINIUM");
        assertFeature(k5000Group, 4, "17", "20W 5000K 1000mm ALUMINIUM");
        assertFeature(k5000Group, 5, "18", "40W 5000K 2000mm ALUMINIUM");

    }


    private void fillInAndSubmitOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "Ima Hungry");
        fillField("input#deliveryStreet", "1234 Culinary Blvd.");
        fillField("input#deliveryCity", "Foodsville");
        fillField("input#deliveryState", "CO");
        fillField("input#deliveryZip", "81019");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "10/24");
        fillField("input#ccCVV", "123");
        browser.findElement(By.cssSelector("form")).submit();
    }

    private void submitEmptyOrderForm() {
        assertThat(browser.getCurrentUrl()).isEqualTo(currentOrderDetailsPageUrl());
        browser.findElement(By.cssSelector("form")).submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(9);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Please correct the problems below and resubmit.",
                "Delivery name is required",
                "Street is required",
                "City is required",
                "State is required",
                "Zip code is required",
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElements = browser.findElements(By.className("validationError"));
        List<String> validationErrors = validationErrorElements.stream()
                .map(el -> el.getText())
                .collect(Collectors.toList());
        return validationErrors;
    }

    private void submitInvalidOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "I");
        fillField("input#deliveryStreet", "1");
        fillField("input#deliveryCity", "F");
        fillField("input#deliveryState", "C");
        fillField("input#deliveryZip", "8");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElement(By.cssSelector("form")).submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(4);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Please correct the problems below and resubmit.",
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElement(By.cssSelector(fieldName));
        field.clear();
        field.sendKeys(value);
    }

    private void assertFeature(WebElement featureGroup,
                               int featureIdx, String id, String name) {
        List<WebElement> proteins = featureGroup.findElements(By.tagName("div"));
        WebElement feature = proteins.get(featureIdx);
        assertThat(
                feature.findElement(By.tagName("input")).getAttribute("value"))
                .isEqualTo(id);
        assertThat(
                feature.findElement(By.tagName("span")).getText())
                .isEqualTo(name);
    }

    private void clickDesignALight() {
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        browser.findElement(By.cssSelector("a[id='design']")).click();
    }

    private void clickBuildAnotherLight() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        browser.findElement(By.cssSelector("a[id='another']")).click();
    }

    //
    // URL helper methods
    //
    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }

}
