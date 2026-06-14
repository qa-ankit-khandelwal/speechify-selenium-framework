package com.qatools.tests;

import com.qatools.base.BaseTest;
import com.qatools.pages.LoginPage;
import com.qatools.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidLoginTest extends BaseTest {

    @Test(description = "TC_LOGIN_001 - Valid credentials should redirect user away from auth page")
    public void testValidLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.doLogin(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));
        String postLoginUrl = loginPage.getCurrentUrl();
        Assert.assertFalse(
            postLoginUrl.contains("/auth/"),
            "Expected redirect away from auth page but URL is still: " + postLoginUrl
        );
    }

}
