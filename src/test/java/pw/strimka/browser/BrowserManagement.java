package pw.strimka.browser;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.List;

public class BrowserManagement {
    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    static Boolean isHeadless = Boolean.parseBoolean(System.getenv("HEADLESS"));

    private static ThreadLocal<Playwright> getPw() {
        return playwrightThreadLocal;
    }

    private static ThreadLocal<Browser> getBrowser() {
        return browserThreadLocal;
    }

    private static ThreadLocal<BrowserContext> getContext() {
        return contextThreadLocal;
    }

    public static Page getPage() {
        if (pageThreadLocal.get() == null) {
            if (getContext().get() == null) {
                if (getBrowser().get() == null) {
                    if (getPw().get() == null) {
                        getPw().set(Playwright.create());
                    }
                    browserThreadLocal.set(getBasicBrowser());
                }
                contextThreadLocal.set(getBasicContext());
            }
            pageThreadLocal.set(contextThreadLocal.get().newPage());
        }
        return pageThreadLocal.get();
    }

    private static BrowserContext getBasicContext() {
        return browserThreadLocal.get().newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080).setRecordVideoDir(Paths.get("./target/video")).setRecordVideoSize(1920, 1080));
    }

    private static Browser getBasicBrowser() {
        List<String> pwArgs = List.of("--lang=en-En");
        return getPw().get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).setArgs(pwArgs));
    }

    public static void removePlaywright() {
        playwrightThreadLocal.set(null);
    }

    public static void removeContext() {
        contextThreadLocal.set(null);
    }

    public static void removePage() {
        pageThreadLocal.set(null);
    }
}
