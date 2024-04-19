package pw.strimka;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class BaseTest {
    private final Playwright playwright = Playwright.create();
    Page page;

    @BeforeEach
    public void beforeEachBase() {
        System.out.println("BaseTest");
        List<String> pwArgs = Collections.singletonList("--lang=en-En");
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(pwArgs));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080).setRecordVideoDir(Paths.get("./target/video")));
        page = context.newPage();
    }

    @AfterEach
    public void after() {
        page.context().close();
        attachVideo();
    }

    @Step("Open url: {url}")
    protected void navigateToPageUrl(String url) {
        page.navigate(url);
    }

    @Step("Make screenshot with name: {name}")
    protected void takeScreenshot(String name) {
        byte[] scr = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./target/screenshots/" + name)));
        Allure.addAttachment("Video recording", new ByteArrayInputStream(scr));
    }

    @Attachment
    private void attachVideo() {
        Video video = page.video();
        try {
            Allure.addAttachment("Video recording", new ByteArrayInputStream(Files.readAllBytes(video.path())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
