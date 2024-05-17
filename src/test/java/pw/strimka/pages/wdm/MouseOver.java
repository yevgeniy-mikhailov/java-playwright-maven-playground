package pw.strimka.pages.wdm;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pw.strimka.pages.BasePage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pw.strimka.browser.BrowserManagement.getPage;

public class MouseOver extends BasePage<MouseOver> {
    @Override
    protected MouseOver getThis() {
        return this;
    }

    @Step("Assert that MouseOver is opened")
    public MouseOver assertMouseOverOpened() {
        assertThat(getPage().locator("text='Mouse over'")).isVisible();
        return this;
    }

    @Step("Assert that all expected pictures loaded in expected order")
    public MouseOver assertAllExpectedImagesLoaded() {
        var imagesLocator = ".figure img";
        Assertions.assertEquals(4, getPage().locator(imagesLocator).all().size());
        var elements = getPage().locator(imagesLocator).all().stream().map(it -> it.getAttribute("src")).toList();
        Assertions.assertIterableEquals(List.of("img/compass.png", "img/calendar.png", "img/award.png", "img/landscape.png"), elements);
        return this;
    }

    @Step("Assert that picture [{pictureName}] has expected caption [{expectedCaption}]")
    public MouseOver assertCaptionUponHovering(String pictureName, String expectedCaption) {
        var picture = getPage().locator(String.format("[src='img/%s.png']", pictureName));
        picture.hover();
        var actualCaption = picture.locator("..").locator("div p.lead").textContent();
        Assertions.assertEquals(expectedCaption, actualCaption);
        return this;
    }
}
