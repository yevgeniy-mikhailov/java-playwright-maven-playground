package pw.strimka.tests.wdm;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Execution(ExecutionMode.SAME_THREAD)
public class WdnNavigationTests extends WdmBaseTest {

    @BeforeEach
    public void openWebFormsPage() {
        navigationPage = wdmPage.openNavigation().waitForPageLoaded("Navigation");
    }

    @AllureId("HW4-2.1")
    @Test
    void verifyPaginationStartState() {
        Assertions.assertAll(
                () -> navigationPage.checkPaginatorIsVisible(),
                () -> assertThat(navigationPage.getPreviousButton()).isVisible(),
                () -> assertThat(navigationPage.getPreviousButton()).hasClass(Pattern.compile("disabled")),

                () -> assertThat(navigationPage.getNextButton()).isVisible(),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),

                () -> assertThat(navigationPage.getPageNumberButton(1)).isVisible(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).hasClass(Pattern.compile("active")), //highlighted

                () -> assertThat(navigationPage.getPageNumberButton(2)).isVisible(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),

                () -> assertThat(navigationPage.getPageNumberButton(3)).isVisible(),
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation1.html"),
                () -> navigationPage.assertPageHasText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")

        );


    }

    @AllureId("HW4-2.2")
    @Test
    void verifyPaginationChangingState() {
        //navigate to 1 page
        navigationPage.getPageNumberButton(1).click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).isVisible(),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation1.html"),
                () -> navigationPage.assertPageHasText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        );
        //navigate to 2 page
        navigationPage.getPageNumberButton(2).click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).not().hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation2.html"),
                () -> navigationPage.assertPageHasText("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
        );
        //navigate to 3 page
        navigationPage.getPageNumberButton(3).click();

        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).not().hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(3)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation3.html"),
                () -> navigationPage.assertPageHasText("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum")
        );
        //Navigate to page 1 and then click on Next button
        navigationPage.getPageNumberButton(1).click();
        navigationPage.getNextButton().click();
        navigationPage.getPageNumberButton(2).click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).not().hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation2.html"),
                () -> navigationPage.assertPageHasText("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
        );

        //Click on Next button
        navigationPage.getNextButton().click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).not().hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(3)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation3.html"),
                () -> navigationPage.assertPageHasText("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum")
        );
        //Click on Previous button
        navigationPage.getPreviousButton().click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).not().hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(2)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation2.html"),
                () -> navigationPage.assertPageHasText("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
        );
        //Click on Previous button
        navigationPage.getPreviousButton().click();
        Assertions.assertAll(
                () -> assertThat(navigationPage.getPreviousButton()).hasClass(Pattern.compile("disabled")),
                () -> assertThat(navigationPage.getNextButton()).isVisible(),
                () -> assertThat(navigationPage.getNextButton()).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(1)).hasClass(Pattern.compile("active")), //highlighted
                () -> assertThat(navigationPage.getPageNumberButton(2)).not().isDisabled(),
                () -> assertThat(navigationPage.getPageNumberButton(3)).not().isDisabled(),
                () -> navigationPage.assertCurrentPageEndsWith("navigation1.html"),
                () -> navigationPage.assertPageHasText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        );
    }
}
