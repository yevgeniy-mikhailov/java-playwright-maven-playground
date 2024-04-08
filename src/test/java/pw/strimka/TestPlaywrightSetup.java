package pw.strimka;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

class TestPlaywrightSetup {
    private final Playwright playwright = Playwright.create();
    private static final String GOOGLE_URL = "https://google.com";
    Browser browser;
    Page page;

    @BeforeEach
    public void beforeEach() {
        List<String> pwArgs = Collections.singletonList("--lang=en-En");
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(pwArgs));
        page = browser.newPage();
    }

    @Test
    void testPlaywrightSetup() {
        navigateToPageUrl(GOOGLE_URL);
        page.locator("//textarea[@name='q']").fill("open playwright documentation");
        page.keyboard().press("Enter");
        page.locator("//h3", new Page.LocatorOptions().setHasText("Installation")).first().click();
        Assertions.assertEquals("Installation | Playwright", page.title());
        takeScreenshot("playwright_documentation.png");
    }

    public void navigateToPageUrl(String url) {
        page.navigate(url);
    }

    public void takeScreenshot(String name) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(name)));
    }
}
