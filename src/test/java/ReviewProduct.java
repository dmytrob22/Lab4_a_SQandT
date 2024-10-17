import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewProduct {
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
    public void testProductReview() {
        // Step 2: Navigate to the URL
        page.navigate("http://automationexercise.com");

        // Step 3: Click on 'Products' button
        page.click("a[href='/products']");

        // Step 4: Verify user is navigated to ALL PRODUCTS page successfully
        assertTrue(page.title().contains("All Products"));

        // Step 5: Click on 'View Product' button
        page.click("a[href='/product_details/1']"); // Assuming it's the first product

        // Step 6: Verify 'Write Your Review' is visible
        assertTrue(page.locator("a:has-text('Write Your Review')").isVisible());

        // Step 7: Enter name, email, and review
        page.fill("input[id='name']", username);
        page.fill("input[id='email']", email);
        page.fill("textarea[id='review']", "This is a test review.");

        // Step 8: Click 'Submit' button
        page.click("button[id='button-review']");

        // Step 9: Verify success message 'Thank you for your review.'
        assertTrue(page.locator("span:has-text('Thank you for your review.')").isVisible());
    }
}
