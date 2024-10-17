import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCartFromRecommended {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "dimb0r22@gmail.com";
    private String password = "11112222";

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
    public void testRecommendedItemsAddToCart() {
        // Step 2: Navigate to the URL
        page.navigate("http://automationexercise.com");

        page.click("a[href='/login']");
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());
        page.fill("input[data-qa='login-email']", email);
        page.fill("input[data-qa='login-password']", password);
        page.click("button[data-qa='login-button']");

        // Step 3: Scroll to bottom of page
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");

        // Step 4: Verify 'RECOMMENDED ITEMS' are visible
        assertTrue(page.locator("h2:has-text('recommended items')").isVisible());

        // Step 5: Click on 'Add To Cart' on Recommended product
        page.click("a[data-product-id='1']");

        // Step 6: Click on 'View Cart' button
        page.click("u:has-text('View Cart')");

        // Step 7: Verify that product is displayed in cart page
        assertTrue(page.locator("tr[id='product-1']").isVisible());
    }
}
