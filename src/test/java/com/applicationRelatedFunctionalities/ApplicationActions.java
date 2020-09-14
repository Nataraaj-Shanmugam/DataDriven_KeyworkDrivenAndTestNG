package com.applicationRelatedFunctionalities;

import com.genericKeywords.GenericKeywords;
import com.genericKeywords.ProjectCustomException;

public class ApplicationActions extends GenericKeywords {
	ApplicationObjects applicationObjectsObj = new ApplicationObjects();

	/**
	 * Function to search products
	 * @author Nataraaj
	 */
	public void search() {
		try {
			inputText(applicationObjectsObj.searchBox_input, getTestData().get("SearchText"));
			waitUntilClickableOfAnElement(10, applicationObjectsObj.searchOptions_link);
			click(applicationObjectsObj.searchOptions_link);
			reporterLog("pass", "Searching products for "+getTestData().get("SearchText"));
		}catch(Exception e) {
			new ProjectCustomException(getClassName(e), getMethodName(e), e, "");
		}
	}
	
	public void ClickOnTodaysDeal() {
		try {
			click(applicationObjectsObj.todaysDeal_link);
			waitUntilPresenceOfText(10, applicationObjectsObj.searchDropDownValue, "Deals");
		}catch(Exception e) {
			new ProjectCustomException(getClassName(e), getMethodName(e), e, "");
		}
	}

	/**
	 * Function to get product name and value
	 * @author Nataraaj
	 */
	public void getProductNameAndValue() {
		try {
			reporterLog("INFO", "Number of products for the Search "+getElements(applicationObjectsObj.productName_text).size());
			reporterLog("pass", "Listed products for "+getTestData().get("SearchText"));
		}catch(Exception e) {
			new ProjectCustomException(getClassName(e), getMethodName(e), e, "");
		}
	}
}
