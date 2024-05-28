package pw.strimka.pages.wdm;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import io.qameta.allure.Step;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.jupiter.api.Assertions;
import pw.strimka.pages.BasePage;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pw.strimka.browser.BrowserManagement.getPage;

public class WebForm extends BasePage<WebForm> {
    private static final String DATEPICKER_LOCATOR = "[name='my-date']";

    @Step("Fill textInput field with {str}")
    public WebForm fillTextInput(String str) {
        getPage().locator("#my-text-id").fill(str);
        return this;
    }

    @Step("Fill password field with {pwd}")
    public WebForm fillPassword(String pwd) {
        getPage().locator("[name='my-password']").fill(pwd);
        return this;
    }

    @Step("Open datepicker")
    public WebForm openDatepicker() {
        getPage().locator(DATEPICKER_LOCATOR).click();
        return this;
    }

    @Step("Select random day in Datepicker")
    public String selectRandomDay() {
        Random random = new Random();
        List<Locator> listDays = getPage().locator("td.day").all();
        Locator locator = listDays.get(random.nextInt(listDays.size()));
        locator.click();
        return locator.textContent();
    }

    @Step("Submit form")
    public List<NameValuePair> submitWebForm() {
        Request request = getPage().waitForRequest("**/submitted-form*",
                new Page.WaitForRequestOptions().setTimeout(2_000),
                () -> getPage().locator("button[type='submit']").click());
        Response response = request.response();
        List<NameValuePair> queryParams;
        try {
            queryParams = URLEncodedUtils.parse(new URI(response.url()), StandardCharsets.UTF_8);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(queryParams); //For debugging purposes
        assertFormReceived();
        Assertions.assertEquals(200, response.status());
        return queryParams;
    }

    @Step("Assert that {paramName} has expected value [{expectedValue}]")
    public void assertParamHasExpectedValue(WebFormSubmitParams paramName, String expectedValue, List<NameValuePair> valuePairList) {
        Assertions.assertEquals(expectedValue, getValueByParamName(paramName, valuePairList));
    }

    @Step("Assert that selected day has expected value [{expectedValue}]")
    public WebForm assertDate(String expectedDay, List<NameValuePair> valuePairList) {
        String datePattern = "MM/dd/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String actualDate = getValueByParamName(WebFormSubmitParams.MY_DATE, valuePairList);
        int actualDay = LocalDate.from(formatter.parse(actualDate)).getDayOfMonth();
        Assertions.assertEquals(Integer.parseInt(expectedDay), actualDay);
        return this;
    }

    private String getValueByParamName(WebFormSubmitParams paramName, List<NameValuePair> valuePairList) {
        var targetElement = valuePairList.stream().filter(e -> e.getName().equals(paramName.getParameterName())).findFirst();
        if (targetElement.isPresent()) {
            return targetElement.get().getValue();
        } else {
            throw new NoSuchElementException("Element not found");
        }
    }

    @Step("Form submitted")
    private void assertFormReceived() {
        assertThat(getPage().getByText("Form Submitted")).isVisible();
        assertThat(getPage().getByText("Received!")).isVisible();
    }

    @Step("Set color")
    public WebForm setColor(String color) {
        getPage().locator("[name='my-colors']").evaluate(String.format("element  => element.value='%s';", color));
        return this;
    }

    public Locator getDisabledElement() {
        return getPage().locator("[name='my-disabled']");
    }

    public Locator getReadonlyElement() {
        return getPage().locator("[name='my-readonly']");
    }

    public Locator getSelectDropdown() {
        return getPage().locator("[name='my-select']");
    }

    @Step("Assert that My select element sends correct data")
    public WebForm assertSelectSending(Locator locator, String expectedValue) {
        getSelectDropdown().selectOption(locator.textContent());
        List<NameValuePair> params = submitWebForm();
        assertParamHasExpectedValue(WebFormSubmitParams.MY_SELECT, expectedValue, params);
        return this;
    }

    public Locator getRangeLocator() {
        return getPage().locator("[name='my-range']");
    }

    private String getRangeAttributeValue(String attributeName) {
        return getRangeLocator().getAttribute(attributeName);
    }

    @Step("Assert limit of [{minOrMax}] to be equal {expectedValue}")
    public WebForm assertRangeLimit(String expectedValue, String minOrMax) {
        Assertions.assertEquals(expectedValue, getRangeAttributeValue(minOrMax));
        return this;
    }

    @Step("Set value for My range element")
    public WebForm setRangeValue(int i) {
        getRangeLocator().evaluate(String.format("element  => element.value='%d'", i));
        return this;
    }

    @Override
    protected WebForm getThis() {
        return this;
    }
}
