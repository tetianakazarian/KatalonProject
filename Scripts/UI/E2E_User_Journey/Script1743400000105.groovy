import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import utils.TestDataStructured

//def user = CustomKeywords.'utils.FakerUtils.generateUser'()
TestDataStructured user = GlobalVariable.testUser
KeywordUtil.logInfo("Generated test user: ${user}")

try {
    CustomKeywords.'pages.HomePage.openHomePage'()
    CustomKeywords.'pages.HomePage.clickSignupLogin'()
    CustomKeywords.'pages.LoginPage.startSignup'(user.name as String, user.email as String)
    CustomKeywords.'pages.SignupPage.completeRegistration'(user)
    CustomKeywords.'pages.SignupPage.verifyAccountCreated'()
    CustomKeywords.'pages.SignupPage.clickContinue'()
    CustomKeywords.'pages.HomePage.verifyLoggedInAs'(user.name as String)
    CustomKeywords.'pages.HomePage.openCategoryAndSubcategory'()

	// since I don't see any filter capability here, just collecting visible products and 
	// filter by price extracting the price from the page item
    List<Map> products = CustomKeywords.'utils.ProductUtils.collectVisibleProducts'()
    Map selected = CustomKeywords.'utils.ProductUtils.getSecondProductAbove500SortedAsc'(products)
    KeywordUtil.logInfo("Selected product after filter/sort: ${selected}")

    CustomKeywords.'utils.RetryUtils.retry'(2, 1500, {
        CustomKeywords.'utils.ProductUtils.addToCartByName'(selected.name as String)
        CustomKeywords.'pages.CartPage.openViewCartModalLink'()
    })

	def category = GlobalVariable.testConfig.testComplexConfig.selectionRule.category
	def subcategory = GlobalVariable.testConfig.testComplexConfig.selectionRule.subcategory
	
    CustomKeywords.'pages.CartPage.verifyCartContainsProduct'(selected.name as String)
    CustomKeywords.'utils.ApiUtils.verifyProductMatchesSelection'(selected.name as String, selected.price as Integer, 
		GlobalVariable.testConfig.testComplexConfig.selectionRule.category,
		GlobalVariable.testConfig.testComplexConfig.selectionRule.subcategory)
    CustomKeywords.'pages.CartPage.proceedToCheckout'()
    CustomKeywords.'pages.CheckoutPage.placeOrder'()
    CustomKeywords.'pages.CheckoutPage.completePayment'()
    CustomKeywords.'pages.CheckoutPage.verifyOrderSuccess'()
} finally {
    WebUI.closeBrowser()
}
