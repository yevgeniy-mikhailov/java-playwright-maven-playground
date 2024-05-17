package pw.strimka.tests.wdm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.strimka.extensions.AttachmentsExtension;
import pw.strimka.extensions.BrowserManagementExtension;
import pw.strimka.pages.wdm.*;

import static pw.strimka.constant.Url.WDM_TEST_SITE;


@ExtendWith({BrowserManagementExtension.class,AttachmentsExtension.class})
public class WdmBaseTest {

    final WdnSiteStart wdmPage = new WdnSiteStart();
    WebForm webFormPage;
    Navigation navigationPage;
    LoadingImages loadingImagesPage;
    MouseOver mouseOverPage;

    @BeforeEach
    void preconditionSetup() {
        wdmPage.navigateToPageUrl(WDM_TEST_SITE);
    }
}
