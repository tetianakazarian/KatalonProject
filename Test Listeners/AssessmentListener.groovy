import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class AssessmentListener {

    private static final KeywordLogger log = new KeywordLogger()

	//customizing logging
    @BeforeTestCase
    def beforeTestCase(TestCaseContext testCaseContext) {
        log.logInfo("=== START TEST CASE: ${testCaseContext.getTestCaseId()} ===")
    }

    @AfterTestCase
    def afterTestCase(TestCaseContext testCaseContext) {
        log.logInfo("=== END TEST CASE: ${testCaseContext.getTestCaseId()} | STATUS: ${testCaseContext.getTestCaseStatus()} ===")
        if ('FAILED'.equalsIgnoreCase(testCaseContext.getTestCaseStatus())) {
            try {
                WebUI.takeScreenshot(FailureHandling.OPTIONAL)
                log.logWarning('Failure screenshot captured.')
            } catch (Exception e) {
                log.logWarning("Could not capture screenshot: ${e.message}")
            }
        }
    }
}
