package pw.strimka.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pw.strimka.browser.BrowserManagement.getPage;

abstract public class BasePage <T extends BasePage<T>>{

    @Step("Wait for page loaded state")
    public T waitForPageLoaded(String pageText) {
        assertThat(getPage().locator("//h1", new Page.LocatorOptions().setHasText(pageText))).isVisible();
        return getThis();
    }

    protected abstract T getThis();

    @Step("Open url: {url}")
    public BasePage navigateToPageUrl(String url) {
        getPage().navigate(url);
        getPage().waitForLoadState(LoadState.DOMCONTENTLOADED);
        return this;
    }

    @Step("Verify that expected page title is equal to actual one")
    public static void assertTitle(String expected, String actual) {
        Assertions.assertEquals(expected, actual);
    }

    @Step("Get the title of page")
    public String getPageTitle() {
        return getPage().title();
    }

    @Step("Go to the previous page opened")
    public void goBack() {
        getPage().goBack();
    }
}
