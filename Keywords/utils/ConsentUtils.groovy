package utils

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// the purpose of this class to get rif of unexpected consents, popups and layers which can randomly make the test failed
class ConsentUtils {

	@Keyword
    def acceptConsentIfPresent() {
        try {
            if (WebUI.waitForElementVisible(findTestObject('HomePage/btn_acceptConsent'), 5, FailureHandling.OPTIONAL)) {
                KeywordUtil.logInfo("Consent popup detected. Accepting...")
                WebUI.click(findTestObject('HomePage/btn_acceptConsent'))
                WebUI.delay(1)
            } else {
                KeywordUtil.logInfo("Consent popup not present")
            }
        } catch (Exception e) {
            KeywordUtil.logInfo("Consent popup handling skipped: ${e.message}")
        }
    }
	
//	@Keyword
//	def hideIframesIfPresent() {
//		try {
//			WebUI.executeJavaScript("""
//            document.querySelectorAll("iframe").forEach(el => {
//                if (
//                    el.id?.startsWith('aswift_') ||
//                    el.id === 'google_esf' ||
//                    el.src?.includes('doubleclick') ||
//                    el.src?.includes('googleads')
//                ) {
//                    el.style.display = 'none';
//                    el.style.visibility = 'hidden';
//                    el.style.pointerEvents = 'none';
//                }
//            });
//        """, null)
//	
//			com.kms.katalon.core.util.KeywordUtil.logInfo("Hide iframes")
//	
//		} catch (Exception e) {
//			com.kms.katalon.core.util.KeywordUtil.logInfo("Ad iframe hiding skipped: ${e.message}")
//		}
//	}
	
	@Keyword
	def removeBlockingAdsAndOverlays() {
		try {
			WebUI.executeJavaScript('''
            document.querySelectorAll("ins.adsbygoogle").forEach(el => el.remove());
            document.querySelectorAll("ins.adsbygoogle-noablate").forEach(el => el.remove());
            document.querySelectorAll("[id^='aswift_'][id$='_host']").forEach(el => el.remove());
            document.querySelectorAll("iframe[id^='aswift_']").forEach(el => el.remove());
            document.querySelectorAll("iframe#google_esf").forEach(el => el.remove());
            document.querySelectorAll("iframe[src*='doubleclick'], iframe[src*='googleads']").forEach(el => el.remove());

            document.querySelectorAll("body *").forEach(el => {
                const s = window.getComputedStyle(el);
                const w = el.offsetWidth;
                const h = el.offsetHeight;

                const looksLikeFullscreenOverlay =
                    (s.position === 'fixed' || s.position === 'absolute') &&
                    w >= window.innerWidth * 0.8 &&
                    h >= window.innerHeight * 0.8 &&
                    s.display !== 'none' &&
                    s.visibility !== 'hidden';

                const looksLikeAdsElement =
                    (el.tagName && el.tagName.toLowerCase() === 'ins' && el.className.includes('adsbygoogle')) ||
                    (el.id && el.id.includes('aswift')) ||
                    (el.src && (el.src.includes('doubleclick') || el.src.includes('googleads')));

                if (looksLikeFullscreenOverlay || looksLikeAdsElement) {
                    el.remove();
                }
            });
        ''', null)
	
			KeywordUtil.logInfo("Removed blocking ads and overlays")
		} catch (Exception e) {
			KeywordUtil.logInfo("Overlay cleanup skipped: ${e.message}")
		}
	}
}