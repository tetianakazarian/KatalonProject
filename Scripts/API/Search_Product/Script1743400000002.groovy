import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

String productName = 'Stylish Dress'
RequestObject request = findTestObject('API/SearchProduct')
request.setBodyContent(new HttpTextBodyContent("search_product=${java.net.URLEncoder.encode(productName, 'UTF-8')}", 'UTF-8', 'application/x-www-form-urlencoded'))

def response = WS.sendRequest(request)
WS.verifyResponseStatusCode(response, 200)

def json = new JsonSlurper().parseText(response.getResponseBodyContent())
assert json.products != null
assert json.products.any { ((it.name ?: '') as String).equalsIgnoreCase(productName) }
