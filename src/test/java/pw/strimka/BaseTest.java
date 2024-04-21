package pw.strimka;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class BaseTest {
    private final Playwright playwright = Playwright.create();
    Page page;
    Boolean isHeadless = Boolean.parseBoolean(System.getenv("HEADLESS"));

    @BeforeEach
    public void beforeEachBase() {
        List<String> pwArgs = Collections.singletonList("--lang=en-En");
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).setArgs(pwArgs));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080).setRecordVideoDir(Paths.get("./target/video")).setRecordVideoSize(1920, 1080));
        page = context.newPage();
    }

    @AfterEach
    public void afterEachBase(TestInfo testInfo) {
        page.context().close();
        attachVideo(optimizeTestName(testInfo));
    }

    @Step("Open url: {url}")
    protected void navigateToPageUrl(String url) {
        page.navigate(url);
    }

    @Step("Make screenshot with name: {name}")
    protected void takeScreenshot(String name) {
        byte[] scr = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./target/screenshots/" + name)));
        Allure.addAttachment(name, new ByteArrayInputStream(scr));
    }

    @Step("Video recording of test: {name}")
    private void attachVideo(String name) {
        Video video = page.video();
        try {
            Allure.addAttachment(name + ".mp4", new ByteArrayInputStream(Files.readAllBytes(video.path())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String optimizeTestName(TestInfo ti) {
        String className = ti.getTestClass().get().getName();
        String methodName = ti.getDisplayName();
        return (className + "_" + methodName.substring(0, methodName.length() - 2)).replace('.', '_');
    }
}
