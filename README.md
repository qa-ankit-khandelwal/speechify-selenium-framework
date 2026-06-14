# Speechify Login — Selenium Automation Framework

Enterprise-grade UI automation framework built with **Selenium 4 + Java + Maven + TestNG** following the **Page Object Model** pattern.

---

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Selenium | 4.25.0 | Browser automation |
| TestNG | 7.9.0 | Test runner & assertions |
| WebDriverManager | 5.9.3 | Auto ChromeDriver management |
| Maven | 3.x | Build & dependency management |

---

## Project Structure

```
Selenium_Framework/
├── pom.xml                                          Maven build + dependencies
├── testng.xml                                       Suite runner (2 test blocks)
└── src/
    ├── main/java/com/qatools/
    │   ├── base/
    │   │   └── BaseTest.java                        WebDriver lifecycle (@BeforeTest / @AfterTest)
    │   ├── pages/
    │   │   └── LoginPage.java                       Page Object Model (PageFactory, XPath-only)
    │   └── utils/
    │       └── ConfigReader.java                    Reads test data from config.properties
    └── test/
        ├── java/com/qatools/tests/
        │   ├── ValidLoginTest.java                  TC_LOGIN_001
        │   └── InvalidLoginTest.java                TC_LOGIN_003 to TC_LOGIN_005
        └── resources/
            └── config.properties.example           Template — copy to config.properties and fill in
```

---

## Prerequisites

- JDK 21+
- Maven 3.6+
- Google Chrome (latest)
- Internet connection (tests run against `speechify.com`)

---

## Setup

**1. Clone the repository**

```bash
git clone https://github.com/qa-ankit-khandelwal/speechify-selenium-framework.git
cd speechify-selenium-framework
```

**2. Create your local config file**

```bash
cp src/test/resources/config.properties.example src/test/resources/config.properties
```

**3. Fill in your Speechify credentials**

```properties
# src/test/resources/config.properties
valid.email=your-real-email@example.com
valid.password=YourRealPassword
```

> `config.properties` is gitignored — your credentials will never be committed.

**4. Install dependencies**

```bash
mvn clean compile
```

---

## Running Tests

**Run the full suite**

```bash
mvn clean test
```

**Run only valid login tests**

```bash
mvn test -Dtest=ValidLoginTest
```

**Run only invalid login tests**

```bash
mvn test -Dtest=InvalidLoginTest
```

---

## Test Cases

### ValidLoginTest

| ID | Description | Expected Result |
|---|---|---|
| TC_LOGIN_001 | Login with valid email and password | Redirected away from `/auth/` page |

### InvalidLoginTest

| ID | Description | Expected Result |
|---|---|---|
| TC_LOGIN_003 | Login with correct email but wrong password | Error message displayed |
| TC_LOGIN_004 | Submit login form with empty fields | Validation blocks login; user stays on auth page |
| TC_LOGIN_005 | Login with malformed email (`notanemail`) | Validation blocks login; user stays on auth page |

---

## Key Design Decisions

**XPath-only locators**
All `@FindBy` annotations use XPath exclusively. No CSS selectors, no `By.id()`, no `By.name()`. XPaths are defined as `private static final String` constants and reused in both `@FindBy` and `WebDriverWait` calls — single source of truth.

**Page-ready guard**
`@BeforeMethod` navigates to the login URL and then waits for `document.readyState === 'complete'` before any test method runs. This prevents `ScriptTimeoutException` caused by interacting with elements while the page JavaScript is still initialising.

**WebDriverWait everywhere**
Every element interaction in `LoginPage` is preceded by an explicit wait (`visibilityOfElementLocated` or `elementToBeClickable`). No `Thread.sleep()` anywhere in the framework.

**Externalised test data**
All credentials and test values live in `config.properties` (gitignored) and are read at runtime via `ConfigReader`. Test classes contain zero hardcoded strings.

**Structured exception handling**
Every public method in `LoginPage` wraps its interaction in a `try-catch (TimeoutException | NoSuchElementException)` block and re-throws as `RuntimeException` with a descriptive context message, making failures immediately diagnosable in CI logs.

---

## Test Reports

After `mvn test`, Surefire reports are available at:

```
target/surefire-reports/
```

---

## Author

**Ankit Khandelwal** — [qa-ankit-khandelwal](https://github.com/qa-ankit-khandelwal)
