package pw.strimka.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import static pw.strimka.browser.BrowserManagement.getPage;
import static pw.strimka.constant.KeyboardButton.ENTER;

public class GooglePage extends BasePage<GooglePage> {

    @Step("Google for [{searchText}] and open first result")
    public GooglePage searchAndOpenFirstResult(String searchText) {
        getPage().locator("//textarea[@name='q']").fill(searchText);
        getPage().keyboard().press(ENTER);
        getPage().locator("//h3", new Page.LocatorOptions().setHasText("Installation")).first().click();
        return this;
    }

    @Override
    protected GooglePage getThis() {
        return this;
    }
}
