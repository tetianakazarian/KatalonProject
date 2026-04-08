package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import internal.GlobalVariable
import utils.ConsentUtils
import utils.WaitUtils
import config.Env
import internal.GlobalVariable

import com.kms.katalon.core.webui.driver.DriverFactory

class HomePage {

	ConsentUtils consentUtils = new ConsentUtils()
	WaitUtils waitUtils = new WaitUtils()

	@Keyword
	def openHomePage() {
		DriverFactory.getWebDriver().manage().window().maximize()
		WebUI.navigateToUrl(Env.baseUrl()/*GlobalVariable.baseUrl*/)
		WebUI.waitForPageLoad(Env.timeout())
		consentUtils.acceptConsentIfPresent()
	}

	@Keyword
	def clickSignupLogin() {
		consentUtils.acceptConsentIfPresent()
		WebUI.click(findTestObject('HomePage/lnk_SignupLogin'))
	}

	@Keyword
	def openCategoryAndSubcategory() {
		WebUI.switchToDefaultContent()
		WebUI.waitForPageLoad(Env.timeout())
		WebUI.delay(1)
	
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
	
		// click women, and then dress by retrieving the necessary category and subcategory from testConfig
		// and passing these params into the Object
		def category = GlobalVariable.testConfig.testComplexConfig.selectionRule.category
		def subcategory = GlobalVariable.testConfig.testComplexConfig.selectionRule.subcategory
		TestObject women = findTestObject('HomePage/lnk_Category', [('category'): category])
		TestObject dress = findTestObject('HomePage/lnk_Subcategory',
										  [
											  ('category'): category,
											  ('subcategory'): subcategory
										  ])
		
		WebUI.executeJavaScript(
			"arguments[0].scrollIntoView({block: 'center'});",
			Arrays.asList(WebUI.findWebElement(women, Env.timeout()))
		)
	
		WebUI.delay(1)
		
		WebUI.executeJavaScript(
			"arguments[0].click();",
			Arrays.asList(WebUI.findWebElement(women, Env.timeout()))
		)
	
		// wait until dress is visible
		WebUI.waitForElementVisible(dress, Env.timeout())
		
		// scroll properly till the element is visible
		WebUI.executeJavaScript(
			"arguments[0].scrollIntoView({block: 'center'});",
			Arrays.asList(WebUI.findWebElement(dress, Env.timeout()))
		)
	
		WebUI.delay(1)
	
		// click dress
		WebUI.executeJavaScript(
			"arguments[0].click();",
			Arrays.asList(WebUI.findWebElement(dress, Env.timeout()))
		)
	}
	

	@Keyword
	def verifyLoggedInAs(String name) {
		consentUtils.removeBlockingAdsAndOverlays()

		String text = WebUI.getText(findTestObject('HomePage/lbl_LoggedInAs'))
		assert text.contains("Logged in as")
		assert text.contains(name)
	}
}