package pw.strimka.browser;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RecordVideoSize;
import com.microsoft.playwright.options.ViewportSize;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BrowserManagement {
    private static final ConcurrentHashMap<Thread, BrowserManagement> INSTANCE_MAP = new ConcurrentHashMap<>();

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private final boolean headless;
    private final ViewportSize viewportSize;
    private final String videoDir;
    private final RecordVideoSize recordVideoSize;

    public BrowserManagement(boolean headless, ViewportSize viewportSize, String videoDir, RecordVideoSize recordVideoSize) {
        this.headless = headless;
        this.viewportSize = viewportSize;
        this.videoDir = videoDir;
        this.recordVideoSize = recordVideoSize;
    }

    private BrowserManagement() {
        this.viewportSize = getDefaultViewPortSize();
        this.headless = Boolean.parseBoolean(System.getenv("HEADLESS"));
        this.videoDir = "./target/video";
        this.recordVideoSize = getDefaultRecordVideoSize();
    }

    private static RecordVideoSize getDefaultRecordVideoSize() {
        return new RecordVideoSize(1920, 1080);
    }

    private ViewportSize getDefaultViewPortSize() {
        return new ViewportSize(1920, 1080);
    }

    public static Page getPage() {
        Thread currentThread = Thread.currentThread();
        return INSTANCE_MAP.computeIfAbsent(currentThread, t -> new BrowserManagement()).getPageInstance();
    }

    public static Page getPage(boolean headless, ViewportSize viewportSize, String videoDir, RecordVideoSize recordVideoSize) {
        Thread currentThread = Thread.currentThread();
        return INSTANCE_MAP.computeIfAbsent(currentThread, t -> new BrowserManagement(headless, viewportSize, videoDir, recordVideoSize)).getPageInstance();
    }

    private Page getPageInstance() {
        if (page == null) {
            page = getContextInstance().newPage();
        }
        return page;
    }

    private BrowserContext getContextInstance() {
        if (context == null) {
            context = getBrowserInstance().newContext(new Browser.NewContextOptions().setViewportSize(this.viewportSize).setRecordVideoDir(Paths.get(this.videoDir)).setRecordVideoSize(this.recordVideoSize));
        }
        return context;
    }

    private Browser getBrowserInstance() {
        if (browser == null) {
            browser = getPlaywrightInstance().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless).setArgs(List.of("--lang=en-En")));
        }
        return browser;
    }

    private Playwright getPlaywrightInstance() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }

    public static void removePlaywright() {
        Thread currentThread = Thread.currentThread();
        INSTANCE_MAP.remove(currentThread);
    }

    public static void removeContext() {
        Thread currentThread = Thread.currentThread();
        INSTANCE_MAP.remove(currentThread);
    }

    public static void removePage() {
        Thread currentThread = Thread.currentThread();
        INSTANCE_MAP.remove(currentThread);
    }

    public static void cleanupForThread() {
        Thread currentThread = Thread.currentThread();
        INSTANCE_MAP.remove(currentThread);
    }
}
