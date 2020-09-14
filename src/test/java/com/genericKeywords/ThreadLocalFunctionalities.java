package com.genericKeywords;

import java.util.HashMap;
import java.util.LinkedList;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

public class ThreadLocalFunctionalities {

	/**Thread Local entities*/
	//Driver Object
	protected static ThreadLocal<WebDriver> threadDriverInstance = new ThreadLocal<WebDriver>();
	public static WebDriver getdriver() {
		return threadDriverInstance.get();
	}

	//Validation Data
	protected ThreadLocal<HashMap<String,String>> validationData = new ThreadLocal<HashMap<String,String>>();
	protected  HashMap<String,String> getValidationData() {
		return validationData.get();
	}

	//Specific scenario keyword details
	protected static ThreadLocal<LinkedList<String>> scenarioKeywordList = new ThreadLocal<LinkedList<String>>();
	protected static LinkedList<String> getScenarioKeyword() {
		return scenarioKeywordList.get();
	}

	//Specific scenario TestData details
	protected static ThreadLocal<HashMap<String,String>> scenarioTestData = new ThreadLocal<HashMap<String,String>>();
	public static HashMap<String,String> getTestData() {
		return scenarioTestData.get();
	}

	//Specific scenario TestData details
	public static ThreadLocal<ExtentTest> extentReporter = new ThreadLocal<>();
	public static ExtentTest getReporter() {
		return extentReporter.get();
	}
}
