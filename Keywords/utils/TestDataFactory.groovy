package utils

import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory as KatalonTestDataFactory
import com.kms.katalon.core.util.KeywordUtil
import config.Env

// class which is responsible for organizing test data - either from file (particular one based on the profile) or by fake generator
class TestDataFactory {

    TestDataStructured getUser(int rowIndex = 1) {
        boolean useFile = (Env.isTestDataFileUsed() as Boolean)

        KeywordUtil.logInfo("useTestDataFile = ${useFile}")

        if (useFile) {
            return getUserFromFile(rowIndex)
        }

        return new FakerUtils().generateUser()
    }
	
	private String resolveDataFileId() {
		String env = Env.envName()?.capitalize() ?: ''
		return "TestData/Users${env}"
	}

    private TestDataStructured getUserFromFile(int rowIndex) {
	    
		String dataFileId = resolveDataFileId()
		
        TestData data = KatalonTestDataFactory.findTestData(dataFileId)

        if (data == null) {
            throw new RuntimeException("Test data file not found: ${dataFileId}")
        }

        if (rowIndex < 1 || rowIndex > data.getRowNumbers()) {
            throw new RuntimeException("Row index ${rowIndex} is out of range. Available rows: ${data.getRowNumbers()}")
        }

        return new TestDataStructured(
            name: "${getValue(data, 'firstName', rowIndex)} ${getValue(data, 'lastName', rowIndex)}",
			firstName: getValue(data, 'firstName', rowIndex),
			lastName: getValue(data, 'lastName', rowIndex),
            email: getValue(data, 'email', rowIndex),
            password: getValue(data, 'password', rowIndex),
            day: getValue(data, 'day', rowIndex),
            month: getValue(data, 'month', rowIndex),
            year: getValue(data, 'year', rowIndex),
            address: getValue(data, 'address', rowIndex),
            country: getValue(data, 'country', rowIndex),
            state: getValue(data, 'state', rowIndex),
            city: getValue(data, 'city', rowIndex),
            zipcode: getValue(data, 'zipcode', rowIndex),
            mobile: getValue(data, 'mobile', rowIndex)
        )
    }

    private String getValue(TestData data, String columnName, int rowIndex) {
        String value = data.getValue(columnName, rowIndex)
        return value != null ? value.trim() : ''
    }
}