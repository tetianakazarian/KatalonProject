package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.ConsentUtils

class LoginPage {

	ConsentUtils consentUtils = new ConsentUtils()
	
    @Keyword
    void startSignup(String name, String email) {
        WebUI.verifyTextPresent('New User Signup!', false)
        WebUI.setText(findTestObject('LoginPage/input_signup_name'), name)
        WebUI.setText(findTestObject('LoginPage/input_signup_email'), email)
		consentUtils.removeBlockingAdsAndOverlays()
        WebUI.click(findTestObject('LoginPage/btn_signup'))
        KeywordUtil.logInfo("Started signup for: ${name} / ${email}")
    }
}
