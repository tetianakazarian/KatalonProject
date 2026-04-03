package utils

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import config.Env
import internal.GlobalVariable

// utilities to make manipulations with products on the product page
class ProductUtils {

    @Keyword
    List<Map> collectVisibleProducts() {
		List<WebElement> cards = WebUI.findWebElements(findTestObject('ProductsPage/lst_VisibleItems'), Env.timeout())

        List<Map> products = []
        cards.each { WebElement card ->
            try {
                String name = card.findElement(By.cssSelector('.productinfo p')).getText().trim()
                String priceText = card.findElement(By.cssSelector('.productinfo h2')).getText().trim()
				Integer price = parsePrice(priceText)
                if (name) {
					products.add([name: name, price: price])
                }
            } catch (Exception ignored) {
                KeywordUtil.logInfo('Skipping a product card because name/price could not be extracted.')
            }
        }
        KeywordUtil.logInfo("Collected visible products: ${products}")
        return products
    }

    @Keyword
    Integer parsePrice(String priceText) {
        return priceText.replaceAll('[^0-9]', '').toInteger()
    }

    @Keyword
    List<Map> filterProductsAbove(List<Map> products, int threshold) {
        List<Map> filtered = products.findAll { (it.price as Integer) > threshold }
        KeywordUtil.logInfo("Filtered products above ${threshold}: ${filtered}")
        return filtered
    }

    @Keyword
    List<Map> sortProductsByPriceAscending(List<Map> products) {
        List<Map> sorted = products.sort { it.price as Integer }
        KeywordUtil.logInfo("Sorted products ascending by price: ${sorted}")
        return sorted
    }

    @Keyword
    Map getSecondProductAbove500SortedAsc(List<Map> products) {
		//retrieve priceThreshold from the test config to filter the product's list by this threshold
		def priceThreshold = GlobalVariable.testConfig.testComplexConfig.selectionRule.priceThreshold
		
		//retrieve itemPosition from the test config to select the particular item
		def itepPosition = GlobalVariable.testConfig.testComplexConfig.selectionRule.itepPosition
		
        List<Map> filtered = filterProductsAbove(products, priceThreshold)
        List<Map> sorted = sortProductsByPriceAscending(filtered)
        if (sorted.size() < itepPosition) {
            throw new RuntimeException("Less than ${itepPosition} products found with price > ${priceThreshold}.")
        }
        Map item = sorted[itepPosition - 1]
        KeywordUtil.logInfo("Selected second matching product: ${item}")
        return item
    }

    @Keyword
    void addToCartByName(String productName) {
        def driver = DriverFactory.getWebDriver()
        List<WebElement> cards = WebUI.findWebElements(findTestObject('ProductsPage/lst_VisibleItems'), Env.timeout())

        WebElement targetCard = cards.find { WebElement card ->
            try {
                return card.findElement(By.cssSelector('.productinfo p')).getText().trim().equalsIgnoreCase(productName.trim())
            } catch (Exception e) {
                return false
            }
        }

        if (targetCard == null) {
            throw new RuntimeException("Could not find product card for: ${productName}")
        }

        try {
		    new org.openqa.selenium.interactions.Actions(driver)
		        .moveToElement(targetCard)
		        .pause(java.time.Duration.ofMillis(500))
		        .perform()
			} catch (Exception ignored) {
		}

        WebElement addToCart = targetCard.findElement(By.xpath(".//a[contains(@class,'add-to-cart')][1]"))
        ((JavascriptExecutor) driver).executeScript('arguments[0].click();', addToCart)
        KeywordUtil.logInfo("Added to cart by product name: ${productName}")
    }
}
