package utils

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import groovy.json.JsonSlurper

// api utils to make additional checks in e2e scenario
class ApiUtils {

    @Keyword
    Map findProductByName(String productName) {
        def response = WS.sendRequest(findTestObject('API/GetProducts'))
		println(response.getResponseText())
		
        WS.verifyResponseStatusCode(response, 200)

        def json = new JsonSlurper().parseText(response.getResponseBodyContent())
        List products = (json.products ?: []) as List
        Map exact = products.find { ((it.name ?: '') as String).equalsIgnoreCase(productName) } as Map

        if (exact == null) {
            throw new AssertionError("Product '${productName}' was not found in /api/productsList")
        }

        KeywordUtil.logInfo("API product match found: ${exact}")
        return exact
    }

    @Keyword
    void verifyProductMatchesSelection(String productName, int expectedPrice, String expectedUserType, String expectedCategory) {
        Map product = findProductByName(productName)
        int apiPrice = (((product.price ?: '') as String).replaceAll('[^0-9]', '')) as Integer
        String apiUserType = (((product.category ?: [:]).usertype ?: [:]).usertype ?: '') as String
        String apiCategory = (((product.category ?: [:]).category ?: '')) as String

        assert apiPrice == expectedPrice : "API price mismatch. expected=${expectedPrice}, actual=${apiPrice}"
        assert apiUserType.equalsIgnoreCase(expectedUserType) : "API user type mismatch. expected=${expectedUserType}, actual=${apiUserType}"
        assert apiCategory.equalsIgnoreCase(expectedCategory) : "API category mismatch. expected=${expectedCategory}, actual=${apiCategory}"

        RequestObject searchRequest = findTestObject('API/SearchProduct')
        String body = "search_product=${java.net.URLEncoder.encode(productName, 'UTF-8')}"
        searchRequest.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(body, 'UTF-8', 'application/x-www-form-urlencoded'))

        def searchResponse = WS.sendRequest(searchRequest)
        WS.verifyResponseStatusCode(searchResponse, 200)
        def searchJson = new JsonSlurper().parseText(searchResponse.getResponseBodyContent())
        assert (searchJson.products ?: []).any { ((it.name ?: '') as String).equalsIgnoreCase(productName) }

        KeywordUtil.logInfo("API cross-check passed for '${productName}' with price ${expectedPrice}, user type ${expectedUserType}, category ${expectedCategory}")
    }
}
