package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import utils.ConsentUtils
import utils.WaitUtils
import com.kms.katalon.core.testobject.TestObject
import config.Env

class CheckoutPage {

	ConsentUtils consentUtils = new ConsentUtils()
	WaitUtils waitUtils = new WaitUtils()
	
    @Keyword
    void placeOrder() {		
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
		
		TestObject placeOrder = findTestObject('CheckoutPage/btn_placeOrder')
		
		// WAIT UNTIL placeOrder IS VISIBLE
		WebUI.waitForElementVisible(placeOrder, Env.timeout())
	
		// SCROLL PROPERLY
		WebUI.executeJavaScript(
			"arguments[0].scrollIntoView({block: 'center'});",
			Arrays.asList(WebUI.findWebElement(placeOrder, Env.timeout()))
		)
	
		WebUI.delay(1)
	
		// CLICK placeOrder
		WebUI.executeJavaScript(
			"arguments[0].click();",
			Arrays.asList(WebUI.findWebElement(placeOrder, Env.timeout()))
		)
		
//		waitUtils.safeClick(placeOrder)

        WebUI.verifyTextPresent('(?i)Payment', true)
        KeywordUtil.logInfo('Opened payment page.')
    }

    @Keyword
    void completePayment() {
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
		
        WebUI.setText(findTestObject('CheckoutPage/input_nameOnCard'), Env.paymentName())
        WebUI.setText(findTestObject('CheckoutPage/input_cardNumber'), Env.paymentCardNumber())
        WebUI.setText(findTestObject('CheckoutPage/input_cvc'), Env.cardCvc())
		WebUI.setText(findTestObject('CheckoutPage/input_expiryYear'), Env.cardExpirityYear())
        WebUI.setText(findTestObject('CheckoutPage/input_expiryMonth'), Env.cardExpirityMonth())
        
        WebUI.click(findTestObject('CheckoutPage/btn_payAndConfirm'))
        KeywordUtil.logInfo('Submitted payment data.')
    }

    @Keyword
    void verifyOrderSuccess() {
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
		
        WebUI.verifyTextPresent('(?i)Order Placed!', true)
        KeywordUtil.logInfo('Order success message verified.')
    }
}
