import config.ConfigLoader
import config.Env
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import utils.TestDataFactory
import utils.TestDataStructured
import pages.HomePage
import pages.LoginPage
import pages.SignupPage
import pages.CartPage
import pages.CheckoutPage
import utils.ProductUtils
import internal.GlobalVariable
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class UserJourneySteps {

    TestDataStructured user
    Map config
    Map selected

    HomePage homePage = new HomePage()
    LoginPage loginPage = new LoginPage()
    SignupPage signupPage = new SignupPage()
    CartPage cartPage = new CartPage()
	CheckoutPage checkoutPage = new CheckoutPage()
    ProductUtils productUtils = new ProductUtils()

    @Given("I open the Automation Exercise home page")
    def openHomePage() {
        config = new ConfigLoader("Include/config/config.json").load()
        user = new TestDataFactory().getUser(1)
        homePage.openHomePage()
    }

    @When("I register a new user")
    def registerUser() {
        homePage.clickSignupLogin()
        loginPage.startSignup(user.name, user.email)
        signupPage.completeRegistration(user)
        signupPage.verifyAccountCreated()
        signupPage.clickContinue()
        homePage.verifyLoggedInAs(user.name)
    }

    @When("I navigate to Women category and Dress subcategory")
    def navigateToCategory() {
        homePage.openCategoryAndSubcategory()
    }

    @When("I select the second product above configured threshold sorted ascending")
    def selectProduct() {
        List<Map> products = productUtils.collectVisibleProducts()
        List<Map> filtered = products.findAll { (it.price as Integer) > (config.testComplexConfig.selectionRule.priceThreshold as Integer) }
        List<Map> sorted = filtered.sort { it.price as Integer }
        selected = sorted[config.testComplexConfig.selectionRule.itepPosition- 1 ]
    }

    @When("I add the selected product to cart")
    def addSelectedProductToCart() {
        productUtils.addToCartByName(selected.name as String)
        cartPage.openViewCartModalLink()
    }

    @When("I complete checkout and payment")
    def completeCheckout() {
        cartPage.proceedToCheckout()
        checkoutPage.placeOrder()
        checkoutPage.completePayment()
    }

    @Then("the order should be placed successfully")
    def verifyOrderSuccess() {
        checkoutPage.verifyOrderSuccess()
        WebUI.closeBrowser()
    }
}