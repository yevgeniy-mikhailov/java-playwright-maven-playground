package pw.strimka;

import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;

import static pw.strimka.assertions.CustomAssert.assertTitle;
import static pw.strimka.constant.KeyboardButton.ENTER;
import static pw.strimka.constant.Url.GOOGLE_URL;

class TestPlaywrightSetup extends BaseTest {

    @Test
    void testPlaywrightSetup() {
        String searchText = "open playwright documentation";
        navigateToPageUrl(GOOGLE_URL);
        searchAndOpenFirstResult(searchText);
        assertTitle("Installation | Playwright", page.title());
        takeScreenshot("playwright_documentation.png");
    }

    @Step("Google for [{searchText}] and open first result")
    private void searchAndOpenFirstResult(String searchText) {
        page.locator("//textarea[@name='q']").fill(searchText);
        page.keyboard().press(ENTER);
        page.locator("//h3", new Page.LocatorOptions().setHasText("Installation")).first().click();
    }
}
