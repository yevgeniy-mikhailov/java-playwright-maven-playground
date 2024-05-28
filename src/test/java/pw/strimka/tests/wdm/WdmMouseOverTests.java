package pw.strimka.tests.wdm;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
public class WdmMouseOverTests extends WdmBaseTest {

    @BeforeEach
    public void openWebFormsPage() {
        mouseOverPage = wdmPage.openMouseOver().waitForPageLoaded("Mouse over");
    }

    @AllureId("HW4-3.3")
    @Test
    void verifyMouseOver() {
        var pictureOneName = "Compass";
        var pictureTwoName = "Calendar";
        var pictureThreeName = "Award";
        var pictureFourName = "Landscape";
        mouseOverPage.assertMouseOverOpened()
                .assertAllExpectedImagesLoaded()
                .assertCaptionUponHovering(pictureOneName.toLowerCase(), pictureOneName)
                .assertCaptionUponHovering(pictureTwoName.toLowerCase(), pictureTwoName)
                .assertCaptionUponHovering(pictureThreeName.toLowerCase(), pictureThreeName)
                .assertCaptionUponHovering(pictureFourName.toLowerCase(), pictureFourName);
    }
}
