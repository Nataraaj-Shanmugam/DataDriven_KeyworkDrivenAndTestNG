package com.genericKeywords;

import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface GenericKeywordsInterface {

	abstract void click(By locator);

	abstract void inputText(By locator, String value);

	abstract void inputTextWithActions(By locator, String value);

	abstract void openBrowser();

	abstract void closeBrowser();

	abstract void loadURL(String URL);

	abstract void navigateBack();

	abstract void navigateForward();

	abstract void refreshPage();

	abstract String getText(By locator);

	abstract String getText(WebElement element);

	abstract String getUrl();

	abstract WebElement getElement(By locator);

	abstract List<WebElement> getElements(By locator);

	abstract void doubleClick(By locator);

	abstract void scrollToAnElement(By locator);

	abstract void dropDown(By locator, String selectionType, String option);

	abstract boolean isBlank(By locator);

	abstract boolean isBlank(String textValue);

	abstract String addinCalender(String entityType, int numbersToAdd, String format);

	abstract void keyboardActions(By locator,String[] keyvalues);

	abstract String getRandomString(int stringLength); 

	//Wait functionalities
	abstract WebElement waitUntilVisibiltyOfAnElement(int timeToWait, By locator);

	abstract WebElement waitUntilVisibiltyOfAnElement(int timeToWait, WebElement locator);

	abstract boolean waitUntilInVisibiltyOfAnElement(int timeToWait, By locator);

	abstract WebElement waitUntilClickableOfAnElement(int timeToWait, By locator);

	abstract WebElement waitUntilClickableOfAnElement(int timeToWait, WebElement locator);

	abstract WebElement waitUntilPresenceOfAnElement(int timeToWait, By locator);

	abstract boolean waitUntilAttributeContainsInElement(int timeToWait, By locator, String attribute, String value);

	abstract boolean waitUntilPresenceOfText(int timeToWait, By locator,String validationText);

	abstract boolean waitUntilPresenceOfText(int timeToWait, WebElement locator,String validationText);

	abstract void waitUntilFrameAvailability(int timeToWait, By locator);

	//Verifications
	abstract boolean verifyElementPresent(By locator);

	abstract boolean verifyElementNotPresent(By locator);

	abstract boolean verifyExactTextPresent(By locator,String validationtext);

	abstract boolean verifyPartialTextPresent(By locator,String validationtext);

	//Reporter functionalities

	abstract void reporterLog(String status, String message) throws ProjectCustomException;

	abstract void takeScreenshotFullPage(String status, String message);

	abstract File takeScreenshotSpecificElement(By locator);

	//property file

	abstract void loadPropertyFile(String status);

	abstract String getClassName(Exception exception);

	abstract String getMethodName(Exception exception);
	
	abstract String getClassName();

	abstract String getMethodName();

}
