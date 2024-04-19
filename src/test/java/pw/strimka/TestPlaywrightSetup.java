package pw.strimka;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static pw.strimka.constant.KeyboardButtons.ENTER;
import static pw.strimka.constant.Url.GOOGLE_URL;

class TestPlaywrightSetup extends BaseTest {

    @Test
    void testPlaywrightSetup() {
        String searchText = "open playwright documentation";
        navigateToPageUrl(GOOGLE_URL);
        searchAndOpenFirstResult(searchText);
        Assertions.assertEquals("Installation | Playwright", page.title());
        takeScreenshot("playwright_documentation.png");
    }

    @Test
    void testPlaywrightSetupSecond() {
        String searchText = "open playwright documentation";
        navigateToPageUrl(GOOGLE_URL);
        searchAndOpenFirstResult(searchText);

        Assertions.assertEquals("Installation | Playwright", page.title());
        takeScreenshot("playwright_documentation.png");
    }

    private void searchAndOpenFirstResult(String searchText) {
        page.locator("//textarea[@name='q']").fill(searchText);
        page.keyboard().press(ENTER);
        page.locator("//h3", new Page.LocatorOptions().setHasText("Installation")).first().click();
    }


}
