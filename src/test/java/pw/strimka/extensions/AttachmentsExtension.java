package pw.strimka.extensions;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Video;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static pw.strimka.browser.BrowserManagement.getPage;

public class AttachmentsExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext extensionContext){
        String className = extensionContext.getTestClass().get().getName();
        String methodName = extensionContext.getTestMethod().get().getName();
        takeScreenshot(String.format("%s.%s", className, methodName));

        getPage().context().close();
        attachVideo(String.format("%s.%s", className, methodName));
    }

    @Step("Video recording of test: {name}")
    void attachVideo(String name) {
        Video video = getPage().video();
        try {
            Allure.addAttachment(name + ".mp4", new ByteArrayInputStream(Files.readAllBytes(video.path())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Make screenshot with name: {name}")
    public void takeScreenshot(String name) {
        byte[] scr = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./target/screenshots/" + name)));
        Allure.addAttachment(name, new ByteArrayInputStream(scr));
    }
}
