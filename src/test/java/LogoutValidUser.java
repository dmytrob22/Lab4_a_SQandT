import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutValidUser {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "dimb0r22@gmail.com";
    private String password = "11112222";
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
    public void testLogout() {
        // Step 2: Navigate to the URL
        page.navigate("http://automationexercise.com");

        // Step 3: Verify that home page is visible successfully
        assertTrue(page.title().contains("Automation Exercise"));

        // Step 4: Click on 'Signup / Login' button
        page.click("a[href='/login']");

        // Step 5: Verify 'Login to your account' is visible
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());

        // Step 6: Enter correct email address and password
        page.fill("input[data-qa='login-email']", email);
        page.fill("input[data-qa='login-password']", password);

        // Step 7: Click 'login' button
        page.click("button[data-qa='login-button']");

        // Step 8: Verify that 'Logged in as username' is visible
        assertTrue(page.locator("a:has-text('Logged in as " + username + "')").isVisible());

        // Step 9: Click 'Logout' button
        page.click("a[href='/logout']");

        // Step 10: Verify that user is navigated to login page
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());
    }
}
