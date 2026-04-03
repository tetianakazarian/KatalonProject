package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import utils.ConsentUtils

class CartPage {

	ConsentUtils consentUtils = new ConsentUtils()
	
    @Keyword
    void openViewCartModalLink() {
        WebUI.waitForElementVisible(findTestObject('ProductsPage/btn_viewCartInModal'), 15)
        WebUI.click(findTestObject('ProductsPage/btn_viewCartInModal'))
        WebUI.verifyTextPresent('Shopping Cart', false)
        KeywordUtil.logInfo('Opened cart from add-to-cart modal.')
    }

    @Keyword
    void verifyCartContainsProduct(String productName) {
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
		
        WebUI.verifyTextPresent(productName, false)
        KeywordUtil.logInfo("Verified cart contains product: ${productName}")
    }

    @Keyword
    void proceedToCheckout() {
		consentUtils.acceptConsentIfPresent()
		consentUtils.removeBlockingAdsAndOverlays()
		
        WebUI.click(findTestObject('CartPage/btn_proceedToCheckout'))
        WebUI.verifyTextPresent('Address Details', false)
        WebUI.verifyTextPresent('Review Your Order', false)
        KeywordUtil.logInfo('Proceeded to checkout.')
    }
}
