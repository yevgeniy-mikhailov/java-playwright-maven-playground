package pw.strimka.tests.wdm;

import com.microsoft.playwright.Locator;
import io.qameta.allure.AllureId;
import org.apache.http.NameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pw.strimka.pages.wdm.WebFormSubmitParams;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Execution(ExecutionMode.SAME_THREAD)
public class WebFormTests extends WdmBaseTest {

    @BeforeEach
    public void openWebFormsPage() {
        webFormPage = wdmPage.openWebForms().waitForPageLoaded("Web form");
    }

    @Test
    @AllureId("HW4-1.1")
    void verifyTextInputAndPassword() {
        String textInput = "TRTRTRTRTR";
        String password = "PWD_SUPER_SECRET";
        webFormPage.fillTextInput(textInput).fillPassword(password);
        List<NameValuePair> params = webFormPage.submitWebForm();
        Assertions.assertAll(() -> webFormPage.assertParamHasExpectedValue(WebFormSubmitParams.MY_TEXT, textInput, params), () -> webFormPage.assertParamHasExpectedValue(WebFormSubmitParams.MY_PASSWORD, password, params));
    }

    @Test
    @AllureId("HW4-1.2")
    void verifyDateAndColors() {
        String color = "#FF0000";
        String day = webFormPage.openDatepicker().selectRandomDay();
        webFormPage.setColor(color);
        List<NameValuePair> params = webFormPage.submitWebForm();
        Assertions.assertAll(() -> webFormPage.assertDate(day, params), () -> webFormPage.assertParamHasExpectedValue(WebFormSubmitParams.MY_COLORS, color.toLowerCase(), params));
    }

    @Test
    @AllureId("HW4-1.3")
    void verifyDisabledAndReadonly() {
        Assertions.assertAll(() -> assertThat(webFormPage.getDisabledElement()).isDisabled(), () -> assertThat(webFormPage.getReadonlyElement()).hasAttribute("readonly", ""));
    }

    @Test
    @AllureId("HW4-1.4")
    void verifySelect() {
        Locator dropdown = webFormPage.getSelectDropdown();
        assertThat(dropdown).isVisible();
        String selected = dropdown.locator("option[selected]").textContent();
        Assertions.assertEquals("Open this select menu", selected);
        List<Locator> options = dropdown.locator("option").all();
        Assertions.assertEquals(4, options.size());
        for (int i = 0; i < options.size(); i++) {
            switch (i) {
                case 0:
                    Assertions.assertEquals("Open this select menu", options.get(i).textContent());
                    webFormPage.assertSelectSending(options.get(i), options.get(i).textContent()).goBack();
                    break;
                case 1:
                    Assertions.assertEquals("One", options.get(i).textContent());
                    Assertions.assertEquals("1", options.get(i).getAttribute("value"));
                    webFormPage.assertSelectSending(options.get(i), String.valueOf(i)).goBack();
                    break;
                case 2:
                    Assertions.assertEquals("Two", options.get(i).textContent());
                    Assertions.assertEquals("2", options.get(i).getAttribute("value"));
                    webFormPage.assertSelectSending(options.get(i), String.valueOf(i)).goBack();
                    break;
                case 3:
                    Assertions.assertEquals("Three", options.get(i).textContent());
                    Assertions.assertEquals("3", options.get(i).getAttribute("value"));
                    webFormPage.assertSelectSending(options.get(i), String.valueOf(i)).goBack();
                    break;
            }
        }
    }

    @Test
    @AllureId("HW4-1.5")
    void verifyRange() {
        var range = webFormPage.getRangeLocator();
        assertThat(range).isVisible();
        webFormPage.assertRangeLimit("0", "min").assertRangeLimit("10", "max").setRangeValue(25);
        List<NameValuePair> params = webFormPage.submitWebForm();
        webFormPage.assertParamHasExpectedValue(WebFormSubmitParams.MY_RANGE, "10", params);
    }
}
