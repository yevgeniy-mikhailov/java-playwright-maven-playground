package pw.strimka.tests.wdm;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
public class WdmLoadingImagesTests extends WdmBaseTest {

    @BeforeEach
    public void openWebFormsPage() {
        loadingImagesPage = wdmPage.openLoadingImages().waitForPageLoaded("Loading images");
    }

    @Test
    @AllureId("HW4-3.1")
    void verifyPicturesLoadedUsingNetworkIdle() {
        loadingImagesPage
                .waitForLoadedUsingNetworkIdle()
                .assertLoadingImagesOpened()
                .assertAllExpectedImagesLoaded();
    }

    @Test
    @AllureId("HW4-3.2")
    void verifyPicturesLoadedUsingProgressBar() {
        loadingImagesPage.waitForSpinnerAbsence()
                .assertLoadingImagesOpened()
                .assertAllExpectedImagesLoaded();
    }
}
