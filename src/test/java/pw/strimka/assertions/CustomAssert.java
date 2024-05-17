package pw.strimka.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class CustomAssert {

    @Step("Verify that expected page title is equal to actual one")
    public static void assertTitle(String expected, String actual) {
        Assertions.assertEquals(expected, actual);
    }
}
