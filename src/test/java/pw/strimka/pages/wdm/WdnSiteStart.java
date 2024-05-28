package pw.strimka.pages.wdm;

import io.qameta.allure.Step;
import pw.strimka.pages.BasePage;

import static pw.strimka.browser.BrowserManagement.getPage;

public class WdnSiteStart extends BasePage<WdnSiteStart> {

    @Step("Open WebForms page")
    public WebForm openWebForms() {
        getPage().getByText("Web Form").click();
        return new WebForm();
    }

    @Step("Open Navigation page")
    public Navigation openNavigation() {
        getPage().getByText("Navigation").click();
        return new Navigation();
    }

    @Step("Open LoadingImages page")
    public LoadingImages openLoadingImages() {
        getPage().getByText("Loading images").click();
        return new LoadingImages();
    }

    @Step("Open MouseOver page")
    public MouseOver openMouseOver() {
        getPage().getByText("Mouse over").click();
        return new MouseOver();
    }

    @Override
    protected WdnSiteStart getThis() {
        return this;
    }
}
