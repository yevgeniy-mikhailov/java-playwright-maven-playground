package pw.strimka.pages.wdm;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pw.strimka.pages.BasePage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pw.strimka.browser.BrowserManagement.getPage;

public class LoadingImages extends BasePage<LoadingImages> {
    @Override
    protected LoadingImages getThis() {
        return this;
    }

    @Step("Wait for spinner to absent")
    public LoadingImages waitForSpinnerAbsence() {
        getPage().locator("#spinner").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
        waitForDoneDisplayed();
        return this;
    }

    @Step("Wait for networkIdle state")
    public LoadingImages waitForLoadedUsingNetworkIdle() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
        waitForDoneDisplayed();
        return this;
    }

    @Step("Wait for 'Done!' text to be displayed on the page")
    private void waitForDoneDisplayed() {
        assertThat(getPage().locator("text='Done!'")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
    }

    @Step("Assert that LoadingImages page is opened")
    public LoadingImages assertLoadingImagesOpened() {
        assertThat(getPage().locator("text='Loading images'")).isVisible();
        return this;
    }

    @Step("Assert that all expected images in expected order are loaded")
    public LoadingImages assertAllExpectedImagesLoaded() {
        var imagesLocator = "#image-container img";
        Assertions.assertEquals(4, getPage().locator(imagesLocator).all().size());
        var elements = getPage().locator(imagesLocator).all().stream().map(it -> it.getAttribute("id")).toList();
        Assertions.assertIterableEquals(List.of("compass", "calendar", "award", "landscape"), elements);
        return this;
    }
}
