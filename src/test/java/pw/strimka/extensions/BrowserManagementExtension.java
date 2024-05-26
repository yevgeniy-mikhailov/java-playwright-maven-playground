package pw.strimka.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pw.strimka.browser.BrowserManagement;

public class BrowserManagementExtension implements AfterEachCallback, AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext extensionContext) {
        BrowserManagement.removePlaywright();
        BrowserManagement.cleanupForThread();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        BrowserManagement.removeContext();
        BrowserManagement.removePage();
    }
}
