package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

// custom retry class to ensure more robust behavior in performing some actions for tricky cases
class RetryUtils {

    @Keyword
    def retry(int attempts = 2, int sleepMillis = 1000, Closure action) {
        Throwable lastError = null
        for (int i = 1; i <= attempts; i++) {
            try {
                KeywordUtil.logInfo("Retry wrapper attempt ${i} of ${attempts}")
                return action.call()
            } catch (Throwable t) {
                lastError = t
                KeywordUtil.logInfo("Attempt ${i} failed: ${t.message}")
                if (i < attempts) {
                    sleep(sleepMillis)
                }
            }
        }
        throw lastError
    }
}
