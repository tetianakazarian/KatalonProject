package utils

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import config.Env

//custom wait utils
class WaitUtils {

	ConsentUtils consentUtils = new ConsentUtils()
	
    @Keyword
	void waitAndClick(String objectPath, int timeout = 15) {
        WebUI.waitForElementClickable(findTestObject(objectPath), timeout)
        WebUI.scrollToElement(findTestObject(objectPath), timeout)
        WebUI.click(findTestObject(objectPath), FailureHandling.STOP_ON_FAILURE)
        KeywordUtil.logInfo("Clicked object: ${objectPath}")
    }

    @Keyword
    void waitAndSetText(String objectPath, String value, int timeout = 15) {
        WebUI.waitForElementVisible(findTestObject(objectPath), timeout)
        WebUI.setText(findTestObject(objectPath), value)
        KeywordUtil.logInfo("Entered text into object: ${objectPath}")
    }
	
	@Keyword
	def safeClick(TestObject object) {
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()

		WebUI.scrollToElement(object, Env.timeout());
		WebUI.waitForElementVisible(object, Env.timeout())		
		WebUI.waitForElementClickable(object, Env.timeout())
		WebUI.click(object)

	}
}
