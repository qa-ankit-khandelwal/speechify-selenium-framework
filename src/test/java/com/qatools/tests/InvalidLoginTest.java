package com.qatools.tests;

import com.qatools.base.BaseTest;
import com.qatools.pages.LoginPage;
import com.qatools.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InvalidLoginTest extends BaseTest {

    @Test(description = "TC_LOGIN_003 - Valid email with wrong password should display an error message")
    public void testLoginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.doLogin(ConfigReader.get("valid.email"), ConfigReader.get("wrong.password"));
        Assert.assertTrue(
            loginPage.isErrorMessageDisplayed(),
            "Expected an error message for wrong password but none was displayed"
        );
    }

    @Test(description = "TC_LOGIN_004 - Submitting empty form should block login and keep user on auth page")
    public void testLoginWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.clickLoginButton();
        Assert.assertTrue(
            loginPage.isErrorMessageDisplayed() || loginPage.getCurrentUrl().contains("/auth/"),
            "Expected validation error or to remain on auth page for empty credentials"
        );
    }

    @Test(description = "TC_LOGIN_005 - Malformed email should block form submission and keep user on auth page")
    public void testLoginWithMalformedEmail() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.doLogin(ConfigReader.get("malformed.email"), ConfigReader.get("some.password"));
        Assert.assertTrue(
            loginPage.isErrorMessageDisplayed() || loginPage.getCurrentUrl().contains("/auth/"),
            "Expected validation error or to remain on auth page for malformed email"
        );
    }
}
