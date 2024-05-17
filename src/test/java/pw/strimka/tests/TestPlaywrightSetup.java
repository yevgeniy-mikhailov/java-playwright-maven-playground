package pw.strimka.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.strimka.extensions.AttachmentsExtension;
import pw.strimka.extensions.BrowserManagementExtension;
import pw.strimka.pages.BasePage;
import pw.strimka.pages.GooglePage;

import static pw.strimka.constant.Url.GOOGLE_URL;

@ExtendWith({BrowserManagementExtension.class, AttachmentsExtension.class})
class TestPlaywrightSetup {
    GooglePage googlePage = new GooglePage();

    @Test
    void testPlaywrightSetup() {
        String searchText = "open playwright documentation";
        googlePage.navigateToPageUrl(GOOGLE_URL);
        googlePage.searchAndOpenFirstResult(searchText);
        BasePage.assertTitle("Installation | Playwright", googlePage.getPageTitle());
    }
}
