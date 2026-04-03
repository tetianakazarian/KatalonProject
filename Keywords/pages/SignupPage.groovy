package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.ConsentUtils
import utils.TestDataStructured
import utils.WaitUtils

class SignupPage {

	ConsentUtils consentUtils = new ConsentUtils()
	WaitUtils waitUtils = new WaitUtils()
	
    @Keyword
	void completeRegistration(TestDataStructured user) {
		consentUtils.removeBlockingAdsAndOverlays()
        WebUI.verifyTextPresent('(?i)Enter Account Information', true)
        WebUI.click(findTestObject('SignupPage/radio_title_mr'))
        WebUI.setText(findTestObject('SignupPage/input_password'), user.password as String)
        WebUI.selectOptionByValue(findTestObject('SignupPage/select_day'), user.day as String, false)
        WebUI.selectOptionByValue(findTestObject('SignupPage/select_month'), user.month as String, false)
        WebUI.selectOptionByValue(findTestObject('SignupPage/select_year'), user.year as String, false)

        WebUI.setText(findTestObject('SignupPage/input_firstName'), user.firstName as String)
        WebUI.setText(findTestObject('SignupPage/input_lastName'), user.lastName as String)
        WebUI.setText(findTestObject('SignupPage/input_address'), user.address as String)
        WebUI.selectOptionByLabel(findTestObject('SignupPage/select_country'), user.country as String, false)
        WebUI.setText(findTestObject('SignupPage/input_state'), user.state as String)
        WebUI.setText(findTestObject('SignupPage/input_city'), user.city as String)
        WebUI.setText(findTestObject('SignupPage/input_zipcode'), user.zipcode as String)
        WebUI.setText(findTestObject('SignupPage/input_mobileNumber'), user.mobile as String)
        WebUI.click(findTestObject('SignupPage/btn_createAccount'))

        KeywordUtil.logInfo("Registration form submitted for email: ${user.email}")
    }

    @Keyword
    void verifyAccountCreated() {
		WebUI.waitForElementVisible(findTestObject('SignupPage/lbl_AccountCreated'), 0)
        WebUI.verifyTextPresent('(?i)ACCOUNT CREATED!', true)
    }

    @Keyword
    void clickContinue() {
		TestObject login = findTestObject('HomePage/lnk_SignupLogin')
        waitUtils.safeClick(login)
    }
}
