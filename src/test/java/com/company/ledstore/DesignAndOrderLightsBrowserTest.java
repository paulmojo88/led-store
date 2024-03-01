package com.company.ledstore;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
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
        assertLandedOnLoginPage();
        doRegistration("testuser", "testpassword");
        assertLandedOnLoginPage();
        doLogin("testuser", "testpassword");
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        clickBuildAnotherLight();
        buildAndSubmitALight("Another Light", 4L, 5L, 6L);
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void testDesignALightPage_EmptyOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignALight();
        assertLandedOnLoginPage();
        doRegistration("testuser2", "testpassword");
        doLogin("testuser2", "testpassword");
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void testDesignALightPage_InvalidOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignALight();
        assertLandedOnLoginPage();
        doRegistration("testuser3", "testpassword");
        doLogin("testuser3", "testpassword");
        assertDesignPageElements();
        buildAndSubmitALight("Basic Light", 1L, 2L, 3L);
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
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
        browser.findElement(By.cssSelector("form#lightForm")).submit();
    }
    private void assertLandedOnLoginPage() {
        assertThat(browser.getCurrentUrl()).isEqualTo(loginPageUrl());
    }

    private void doRegistration(String username, String password) {
        browser.findElement(By.linkText("here")).click();
        assertThat(browser.getCurrentUrl()).isEqualTo(registrationPageUrl());
        browser.findElement(By.name("username")).sendKeys(username);
        browser.findElement(By.name("password")).sendKeys(password);
        browser.findElement(By.name("confirm")).sendKeys(password);
        browser.findElement(By.name("fullName")).sendKeys("Test McTest");
        browser.findElement(By.name("street")).sendKeys("1234 Test Street");
        browser.findElement(By.name("city")).sendKeys("Testville");
        browser.findElement(By.name("state")).sendKeys("TX");
        browser.findElement(By.name("zip")).sendKeys("12345");
        browser.findElement(By.name("phoneNumber")).sendKeys("123-123-1234");
        browser.findElement(By.cssSelector("form#registerForm")).submit();
    }


    private void doLogin(String username, String password) {
        browser.findElement(By.cssSelector("input#username")).sendKeys(username);
        browser.findElement(By.cssSelector("input#password")).sendKeys(password);
        browser.findElement(By.cssSelector("form#loginForm")).submit();
    }

    private void doLogout() {
        WebElement logoutForm = browser.findElement(By.cssSelector("form#logoutForm"));
        if (logoutForm != null) {
            logoutForm.submit();
        }
    }

    private void assertDesignPageElements() {
        List<WebElement> featureGroups = browser.findElements(By.className("feature-group"));
        assertThat(featureGroups.size()).isEqualTo(3);

        WebElement k3000Group = browser.findElement(By.cssSelector("div.feature-group#temperature3000"));
        List<WebElement> k3000s = k3000Group.findElements(By.tagName("div"));
        assertThat(k3000s.size()).isEqualTo(6);
        assertFeature(k3000Group, 0, 1L, "10W 3000K 500mm WOOD");
        assertFeature(k3000Group, 1, 2L, "20W 3000K 1000mm WOOD");
        assertFeature(k3000Group, 2, 3L, "40W 3000K 2000mm WOOD");
        assertFeature(k3000Group, 3, 10L, "10W 3000K 500mm ALUMINIUM");
        assertFeature(k3000Group, 4, 11L, "20W 3000K 1000mm ALUMINIUM");
        assertFeature(k3000Group, 5, 12L, "40W 3000K 2000mm ALUMINIUM");

        WebElement k4000Group = browser.findElement(By.cssSelector("div.feature-group#temperature4000"));
        List<WebElement> k4000s = k4000Group.findElements(By.tagName("div"));
        assertThat(k4000s.size()).isEqualTo(6);
        assertFeature(k4000Group, 0, 4L, "10W 4000K 500mm ALUMINIUM");
        assertFeature(k4000Group, 1, 5L, "20W 4000K 1000mm ALUMINIUM");
        assertFeature(k4000Group, 2, 6L, "40W 4000K 2000mm ALUMINIUM");
        assertFeature(k4000Group, 3, 13L, "10W 4000K 500mm WOOD");
        assertFeature(k4000Group, 4, 14L, "20W 4000K 1000mm WOOD");
        assertFeature(k4000Group, 5, 15L, "40W 4000K 2000mm WOOD");

        WebElement k5000Group = browser.findElement(By.cssSelector("div.feature-group#temperature5000"));
        List<WebElement> k5000s = k5000Group.findElements(By.tagName("div"));
        assertThat(k5000s.size()).isEqualTo(6);
        assertFeature(k5000Group, 0, 7L, "10W 5000K 500mm WOOD");
        assertFeature(k5000Group, 1, 8L, "20W 5000K 1000mm WOOD");
        assertFeature(k5000Group, 2, 9L, "40W 5000K 2000mm WOOD");
        assertFeature(k5000Group, 3, 16L, "10W 5000K 500mm ALUMINIUM");
        assertFeature(k5000Group, 4, 17L, "20W 5000K 1000mm ALUMINIUM");
        assertFeature(k5000Group, 5, 18L, "40W 5000K 2000mm ALUMINIUM");

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
        browser.findElement(By.cssSelector("form#orderForm")).submit();
    }

    private void submitEmptyOrderForm() {
        assertThat(browser.getCurrentUrl()).isEqualTo(currentOrderDetailsPageUrl());
        // clear fields automatically populated from user profile
        fillField("input#deliveryName", "");
        fillField("input#deliveryStreet", "");
        fillField("input#deliveryCity", "");
        fillField("input#deliveryState", "");
        fillField("input#deliveryZip", "");
        browser.findElement(By.cssSelector("form#orderForm")).submit();

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
        browser.findElement(By.cssSelector("form#orderForm")).submit();

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

    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElements = browser.findElements(By.className("validationError"));
        return validationErrorElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElement(By.cssSelector(fieldName));
        field.clear();
        field.sendKeys(value);
    }

    private void assertFeature(WebElement featureGroup,
                                  int featureIdx, Long id, String name) {
        List<WebElement> proteins = featureGroup.findElements(By.tagName("div"));
        WebElement feature = proteins.get(featureIdx);
        assertThat(Long.valueOf(feature.findElement(By.tagName("input")).getAttribute("value"))).isEqualTo(id);
        assertThat(feature.findElement(By.tagName("span")).getText()).isEqualTo(name);
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
    private String loginPageUrl() {
        return homePageUrl() + "login";
    }

    private String registrationPageUrl() {
        return homePageUrl() + "register";
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
