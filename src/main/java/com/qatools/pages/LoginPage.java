package com.qatools.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private static final String EMAIL_XPATH    = "//input[@type='email']";
    private static final String PASSWORD_XPATH = "//input[@type='password']";
    private static final String BTN_XPATH      = "//button[@type='submit']";
    private static final String ERROR_XPATH    =
        "//*[@role='alert'] | //p[contains(@class,'error')] | //div[contains(@class,'error')] | //span[contains(@class,'error')]";

    private final WebDriver     driver;
    private final WebDriverWait wait;

    @FindBy(xpath = EMAIL_XPATH)    private WebElement emailInput;
    @FindBy(xpath = PASSWORD_XPATH) private WebElement passwordInput;
    @FindBy(xpath = BTN_XPATH)      private WebElement loginButton;
    @FindBy(xpath = ERROR_XPATH)    private WebElement errorMessage;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EMAIL_XPATH)));
            emailInput.clear();
            emailInput.sendKeys(email);
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Email input not visible or not found on page: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PASSWORD_XPATH)));
            passwordInput.clear();
            passwordInput.sendKeys(password);
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Password input not visible or not found on page: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(BTN_XPATH)));
            loginButton.click();
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Login button not clickable or not found on page: " + e.getMessage());
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_XPATH)));
            return errorMessage.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_XPATH)));
            return errorMessage.getText().trim();
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Error message element not visible or not found on page: " + e.getMessage());
        }
    }

    public void doLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
