import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupWithExistingEmail {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "dimb0r22@gmail.com";
    private String username = "dmytrob22";

    @BeforeEach
    public void setup() {
        // Step 1: Launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        // Close browser after each test
        browser.close();
        playwright.close();
    }

    @Test
    public void testSignupWithExistingEmail() {
        // Step 2: Navigate to the URL
        page.navigate("http://automationexercise.com");

        // Step 3: Verify that home page is visible successfully
        assertTrue(page.title().contains("Automation Exercise"));

        // Step 4: Click on 'Signup / Login' button
        page.click("a[href='/login']");

        // Step 5: Verify 'New User Signup!' is visible
        assertTrue(page.locator("h2:has-text('New User Signup!')").isVisible());

        // Step 6: Enter name and already registered email address
        page.fill("input[data-qa='signup-name']", username);
        page.fill("input[data-qa='signup-email']", email); // Use an already registered email

        // Step 7: Click 'Signup' button
        page.click("button[data-qa='signup-button']");

        // Step 8: Verify error 'Email Address already exist!' is visible
        assertTrue(page.locator("p:has-text('Email Address already exist!')").isVisible());
    }
}
