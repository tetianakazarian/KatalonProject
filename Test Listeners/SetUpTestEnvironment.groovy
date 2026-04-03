import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.chrome.ChromeDriver
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebDriver
import utils.TestDataFactory
import config.Env
import internal.GlobalVariable
import config.ConfigLoader
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.cucumber.keyword.internal.CucumberGlueGenerator

class SetUpTestEnvironment {

    @BeforeTestCase
    def setupBrowser() {

		// setup custom option for Chrome in order to get rid autosave popups and password manager
        ChromeOptions options = new ChromeOptions()

        Map<String, Object> prefs = new HashMap<>()
        prefs.put("credentials_enable_service", false)
        prefs.put("profile.password_manager_enabled", false)
        prefs.put("autofill.profile_enabled", false)
        prefs.put("autofill.credit_card_enabled", false)

        options.setExperimentalOption("prefs", prefs)

        WebDriver driver = new ChromeDriver(options)
        DriverFactory.changeWebDriver(driver)
    }
	
    
	@BeforeTestCase
    def initializeTestData() {
		
		// log the loaded profile which is parsed in Keywords/config/Env.groovy
		KeywordUtil.logInfo(Env.envName())
		KeywordUtil.logInfo(Env.baseUrl())
		KeywordUtil.logInfo(Env.timeout() as String)
		
		// initialize test data either from csv file or from Fake generator (useTestDataFile - boolean param in the profile)
		// takes the first row in the cvs, if user is read from csv file - can be parameterized as well
		// test user data is stored in GlobalVariables to be used across whole project
		GlobalVariable.testUser = new TestDataFactory().getUser(1) 
		
		KeywordUtil.logInfo(GlobalVariable.testUser.name)
		
		//initialize complex test data from config json		
		def configLoader = new ConfigLoader("Include/config/config.json")		
		GlobalVariable.testConfig = configLoader.load()
		
		KeywordUtil.logInfo(GlobalVariable.testConfig.testComplexConfig.selectionRule.category)		
	}
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext context) {
		CucumberGlueGenerator.addDefaultPackages()
	}
}