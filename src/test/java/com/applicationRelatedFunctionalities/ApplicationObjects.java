package com.applicationRelatedFunctionalities;

import org.openqa.selenium.By;

public class ApplicationObjects {

	public By searchBox_input = By.xpath("//input[@id='twotabsearchtextbox']");
	public By searchOptions_link = By.xpath("//div[@class='s-suggestion']");
	public By productName_text = By.xpath("//a[@class='a-link-normal a-text-normal']/span");
	public By productRate_text = By.className("a-price-whole");
	public By todaysDeal_link = By.linkText("Today's Deals");
	public By searchDropDownValue = By.xpath("//div[@class='nav-search-facade']/span");
}
