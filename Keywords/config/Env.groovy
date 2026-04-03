package config

import internal.GlobalVariable

class Env {

    static String baseUrl() {
        return GlobalVariable.baseUrl
    }

    static String defaultPassword() {
        return GlobalVariable.defaultPassword
    }

    static String paymentName() {
        return GlobalVariable.paymentName
    }

    static String paymentCardNumber() {
        return GlobalVariable.paymentCardNumber
    }
	
	static String cardCvc() {
		return GlobalVariable.paymentCvc
	}
	
	static String cardExpirityMonth() {
		return GlobalVariable.paymentMonth
	}
	
	static String cardExpirityYear() {
		return GlobalVariable.paymentYear
	}
		
    static int timeout() {
        return (GlobalVariable.defaultTimeout as Integer)
    }
	
	static boolean isTestDataFileUsed() {
        return GlobalVariable.useTestDataFile
    }

    static boolean isTest() {
        return envName() == 'test'
    }

    static boolean isUat() {
        return envName() == 'uat'
    }

    static boolean isDefault() {
        return envName() == 'default'
    }
	
	static String envName() {
		return GlobalVariable.envName?.toLowerCase()
	}
}