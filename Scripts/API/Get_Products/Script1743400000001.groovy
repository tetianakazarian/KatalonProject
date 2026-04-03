import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

def response = WS.sendRequest(findTestObject('API/GetProducts'))
WS.verifyResponseStatusCode(response, 200)
def json = new JsonSlurper().parseText(response.getResponseBodyContent())
assert json.products != null
assert json.products.size() > 0
