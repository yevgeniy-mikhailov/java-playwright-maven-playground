package pw.strimka.pages.wdm;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import pw.strimka.pages.BasePage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pw.strimka.browser.BrowserManagement.getPage;

public class Navigation extends BasePage<Navigation> {

    @Step("Assert that pagination is visible")
    public Navigation checkPaginatorIsVisible() {
        assertThat(getPaginator()).isVisible();
        return this;
    }

    private Locator getPaginator() {
        return getPage().locator("ul.pagination");
    }

    public Locator getPreviousButton() {
        return getPaginator().locator("li.page-item").getByText("Previous").locator("xpath=..");
    }

    public Locator getNextButton() {
        return getPaginator().locator("li.page-item").getByText("Next").locator("xpath=..");
    }

    public Locator getPageNumberButton(int pageNumber) {
        return getPaginator().locator("li.page-item").getByText(String.valueOf(pageNumber)).locator("xpath=..");
    }

    @Step("Assert that current page URL ends with {url}")
    public void assertCurrentPageEndsWith(String url) {
        assertThat(getPage()).hasURL(Pattern.compile(url));
    }

    @Step("Assert that text [{text}] is visible on the page")
    public void assertPageHasText(String text) {
        assertThat(getPage().getByText(text)).isVisible();
    }

    @Override
    protected Navigation getThis() {
        return this;
    }
}
