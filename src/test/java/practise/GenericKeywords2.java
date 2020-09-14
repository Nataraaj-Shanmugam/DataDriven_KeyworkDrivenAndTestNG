package practise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.genericKeywords.ProjectCustomException;
import com.genericKeywords.ThreadLocalFunctionalities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GenericKeywords2 extends ThreadLocalFunctionalities{

	private static String propertyFilePath = "src/test/resources/ApplicationAndExecution.properties";
	public static String reportPath ;
	public static Properties propFile;
	static {
		new GenericKeywords2().loadPropertyFile(propertyFilePath);
		reportPath = System.getProperty("user.dir")+"\\"+GenericKeywords2.propFile.getProperty("ReportsLocation")+"\\"+"Test Execution Report_"+Calendar.getInstance().getTime().toString().replace(" ", "_").replace(":", "_");
	}

	Consumer<By> click = (locator) -> {
		try {
			getElement(locator).click();
			reporterLog("INFO", "Click on element");
		}catch(Exception e) {
			try {
				((JavascriptExecutor)getdriver()).executeScript("arguments[0].click();", locator);
				reporterLog("INFO", "Click on element");
			}catch(Exception e1) {
				try {
					new Actions(getdriver()).moveToElement(getElement(locator)).click(getElement(locator)).perform();
					reporterLog("INFO", "Click on element");
				}catch(Exception e2) {
					new ProjectCustomException(getClassName(), getMethodName(), e2,"Unable to click on element "+locator);
				}
			}
		}
	
	};
	

	
	public void inputText(By locator, String value) {
		try {
			getElement(locator).sendKeys(value);
			reporterLog("INFO", "Entered text : '"+value+"'");
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to enter '"+value+"' value to element "+locator);
		}
	}

	
	public void inputTextWithActions(By locator, String value) {
		try {
			new Actions(getdriver()).moveToElement(getElement(locator)).sendKeys(getElement(locator), Keys.CLEAR, value).perform();
			reporterLog("INFO", "Entered text :"+value);
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to enter '"+value+"' value to element "+locator);
		}
	}

	
	public void openBrowser() {
		WebDriver driver = null;
		String browserName = getTestData().get("Browser");
		String browserVersion = getTestData().get("Version");
		String defaultDownloadPath = System.getProperty("user.dir")+"\\"+propFile.getProperty("DefaultDownloadPath").replace("/", "\\");
		MutableCapabilities capabilities;
		boolean flag = true;
		switch (browserName) {
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			if(propFile.getProperty("ExecutionType").equals("Local")) {
				capabilities = new ChromeOptions();
				HashMap<String, Object> capPref= new HashMap<>();
				capPref.put("download.default_directory", defaultDownloadPath);
				capPref.put("browser.set_download_behavior", "{ behavior: 'allow' , downloadPath: '"+defaultDownloadPath+"'}");
				((ChromeOptions)capabilities).setHeadless(BooleanUtils.toBoolean(propFile.getProperty("Headless").toLowerCase()));
				((ChromeOptions)capabilities).addArguments("start-maximized");
				((ChromeOptions)capabilities).addArguments("--incognito");
				((ChromeOptions)capabilities).setExperimentalOption("prefs", capPref);
				driver = new ChromeDriver(((ChromeOptions)capabilities));
			}else {
				//code for remote execution
			}
			break;
		case "Firefox":
			if(isBlank(browserVersion))
				WebDriverManager.firefoxdriver().setup();
			else
				WebDriverManager.firefoxdriver().driverVersion(browserVersion).setup();
			if(propFile.getProperty("ExecutionType").equals("Local")) {
				capabilities = new FirefoxOptions();
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("browser.download.folderList", 2);
				//				profile.setPreference("download.default_directory", defaultDownloadPath);
				profile.setPreference("browser.download.dir", defaultDownloadPath);
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/java-archive, application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");
				((FirefoxOptions)capabilities).setHeadless(BooleanUtils.toBoolean(propFile.getProperty("Headless").toLowerCase()));
				((FirefoxOptions)capabilities).addArguments("start-maximized");
				((FirefoxOptions)capabilities).setProfile(profile);
				driver = new FirefoxDriver(((FirefoxOptions)capabilities));
			}else {
				//code for remote execution
			}
			break;
		case "IE":
			WebDriverManager.iedriver().setup();
			if(propFile.getProperty("ExecutionType").equals("Local")) {
				driver = new InternetExplorerDriver();
			}else {
				//code for remote execution
			}
			break;
		case "Edge":
			if(isBlank(browserVersion))
				WebDriverManager.edgedriver().driverVersion(browserVersion).setup();
			else
				WebDriverManager.edgedriver().setup();
			if(propFile.getProperty("ExecutionType").equals("Local")) {

				driver = new EdgeDriver();
			}else {
				//code for remote execution
			}
			break;
		default:
			flag= false;
			break;
		}
		threadDriverInstance.set(driver);
		if(flag) {
			if(isBlank(browserVersion))
				reporterLog("INFO", "Created "+browserName+" browser instance");
			else
				reporterLog("INFO", "Created "+browserName+" "+browserVersion+" version browser instance");
		}else {
			Exception exceptionObject = new Exception();
			new ProjectCustomException(getClassName(exceptionObject), getMethodName(exceptionObject),  exceptionObject,"Unable to recoganize "+browserName+" browser details");
		}
		loadURL.accept(propFile.getProperty("URL"));
	}


	
	public void closeBrowser() {
		getdriver().quit();
	}

	Consumer<String> loadURL = (URL) -> {
		try {
			getdriver().navigate().to(URL);
			reporterLog("INFO", "Loaded URL : "+URL);
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to load URL '"+URL+"'");
		}
	};
	

	
	public void navigateBack() {
		try {
			getdriver().navigate().back();
			reporterLog("INFO", "Navigated to previous page");
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e, "Unable to navigate back to previous page");
		}
	}

	
	public void navigateForward() {
		try {
			getdriver().navigate().forward();
			reporterLog("INFO", "Navigated to next page");
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e, "Unable to navigate to next page");
		}
	}

	
	public void refreshPage() {
		getdriver().navigate().refresh();
		reporterLog("INFO", "Refreshed webpage");
	}

	
	public String getText(By locator) {
		return getText(getElement(locator));
	}
	
	Function<By, String> getText = locator -> getText(getElement(locator));
	
//	Function<WebElement, String> getText = element -> getText(element);
	
	public String getText(WebElement element) {
		waitUntilVisibiltyOfAnElement(NumberUtils.toInt(propFile.getProperty("DefaultWaitTime")), element);
		return element.getText();
	}

	
	public String getUrl() {
		return getdriver().getCurrentUrl();
	}

	
	public WebElement getElement(By locator) {
		return waitUntilVisibiltyOfAnElement(NumberUtils.toInt(propFile.getProperty("DefaultWaitTime")), locator);
	}

	
	public List<WebElement> getElements(By locator) {
		return getdriver().findElements(locator);
	}

	
	public void doubleClick(By locator) {
		try {
			new Actions(getdriver()).moveToElement(getElement(locator)).doubleClick(getElement(locator)).perform();
			reporterLog("INFO", "Double clicked on element: "+locator);
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to perform Actions double click for an element :"+locator);
		}
	}

	
	public void scrollToAnElement(By locator) {
		try {
			((JavascriptExecutor)getdriver()).executeScript("arguments[0].click();", locator);
			reporterLog("INFO", "Scrolled to element :"+locator);
		}catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to perform Actions Scroll to an element :"+locator);
		}
	}

	
	synchronized public void takeScreenshotFullPage(String status, String message) {
		File destinationFile = new File(reportPath+"/"+"Test_Execution_Screenshot_"+Calendar.getInstance().getTime().toString().replace(" ", "_").replace(":", "_")+".png");
		try {
			FileUtils.copyFile(((TakesScreenshot)getdriver()).getScreenshotAs(OutputType.FILE), destinationFile);
		} catch (WebDriverException | IOException e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to perform screenshot process copy operation");
		}

		switch (status.toUpperCase()) {
		case "PASS":
			getReporter().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(destinationFile.getAbsolutePath()).build());
			break;
		case "ERROR":
			getReporter().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(destinationFile.getAbsolutePath()).build());
			break;
		default:
			break;
		}
	}

	
	public File takeScreenshotSpecificElement(By locator) {
		return getElement(locator).getScreenshotAs(OutputType.FILE);
	}

	
	public void dropDown(By locator, String selectionType, String option) {
		boolean flag=false;
		try {
			if(locator.toString().contains("select")) {
				Select dropdown = new Select(getElement(locator));
				switch (selectionType.toLowerCase()) {
				case "index":
					dropdown.selectByIndex(NumberUtils.toInt(option));
					flag=true;
					break;
				case "value":
					dropdown.selectByValue(option);
					flag=true;
					break;
				case "text":
					dropdown.selectByVisibleText(option);
					flag=true;
					break;
				default:
					break;
				}
			}else {
				for (WebElement eachElement : getElements(locator)) {
					if(eachElement.getAttribute(selectionType).equals(option)) {
						eachElement.click();
						flag=true;
						break;
					}
				}
			}
			if(flag)
				reporterLog("INFO", "Selected "+option+" "+selectionType+" in the dropdown element :"+locator);
			else
				throw new Exception("Unable to choose any element in the dropdown");
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to find the "+option+" "+selectionType+" in the dropdown element :"+locator);
		}
	}

	
	public boolean isBlank(By locator) {
		return isBlank(getText(locator));
	}

	
	public boolean isBlank(String stringValue) {
		if(stringValue.trim().isEmpty())
			return true;
		else 
			return false;
	}


	
	public String addinCalender(String entityType, int numbersToAdd, String format) {
		Calendar calObj = Calendar.getInstance();
		SimpleDateFormat  simpleDateFormatObject = new SimpleDateFormat(format);
		try {
			if(!isBlank(propFile.getProperty("TimeZone")))
				simpleDateFormatObject.setTimeZone(TimeZone.getTimeZone(propFile.getProperty("TimeZone")));
			else
				simpleDateFormatObject.setTimeZone(TimeZone.getTimeZone("EST"));

			switch (entityType.toLowerCase()) {
			case "year":
				calObj.set(Calendar.YEAR, calObj.get(Calendar.YEAR)+1);
				break;
			case "month":
				calObj.set(Calendar.MONTH, calObj.get(Calendar.MONTH)+1);
				break;
			case "date":
				calObj.set(Calendar.DATE, calObj.get(Calendar.DATE)+1);
				break;
			case "hour":
				calObj.set(Calendar.HOUR_OF_DAY, calObj.get(Calendar.HOUR_OF_DAY)+1);
				break;
			case "minute":
				calObj.set(Calendar.MINUTE, calObj.get(Calendar.MINUTE)+1);
				break;
			case "second":
				calObj.set(Calendar.SECOND, calObj.get(Calendar.SECOND)+1);
				break;
			default: break;
			}
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to process Calender opertaions for "+entityType+" in the specified '"+format+"' format ");
		}
		return simpleDateFormatObject.format(calObj.getTime());
	}

	
	public void keyboardActions(By locator,String[] keyvalues) {
		new Actions(getdriver()).sendKeys(getElement(locator),Keys.CLEAR, Keys.chord(keyvalues)).perform();
	}

	
	public String getRandomString(int stringLength) {
		return RandomStringUtils.randomAlphabetic(stringLength);
	}

	
	public WebElement waitUntilVisibiltyOfAnElement(int timeToWait, By locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	
	public WebElement waitUntilVisibiltyOfAnElement(int timeToWait, WebElement locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.visibilityOf(locator));
	}
	
	public boolean waitUntilInVisibiltyOfAnElement(int timeToWait, By locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	
	public WebElement waitUntilClickableOfAnElement(int timeToWait, By locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.elementToBeClickable(locator));
	}

	
	public WebElement waitUntilClickableOfAnElement(int timeToWait, WebElement locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.elementToBeClickable(locator));
	}

	
	public WebElement waitUntilPresenceOfAnElement(int timeToWait, By locator) {
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	
	public boolean waitUntilAttributeContainsInElement(int timeToWait, By locator, String attribute, String validationText) {
		waitUntilPresenceOfAnElement(timeToWait, locator);
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.attributeContains(locator, attribute, validationText));
	}

	
	public boolean waitUntilPresenceOfText(int timeToWait, By locator,String validationText) {
		waitUntilPresenceOfAnElement(timeToWait, locator);
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.textToBePresentInElementLocated(locator, validationText));
	}

	
	public boolean waitUntilPresenceOfText(int timeToWait, WebElement locator,String validationText) {
		waitUntilVisibiltyOfAnElement(timeToWait, locator);
		return new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.textToBePresentInElement(locator, validationText));
	}
	
	public void waitUntilFrameAvailability(int timeToWait, By locator) {
		new WebDriverWait(getdriver(), timeToWait).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	
	public boolean verifyElementPresent(By locator) {
		if(getElements(locator).size() > 0)
			return true;
		else
			return false;
	}

	
	public boolean verifyElementNotPresent(By locator) {
		return (!verifyElementPresent(locator));
	}

	
	public boolean verifyExactTextPresent(By locator,String validationtext) {
		boolean flag =false;
		if(getText(locator).equals(validationtext))
			flag=true;
		return flag;
	}

	
	public boolean verifyPartialTextPresent(By locator,String validationtext) {
		boolean flag =false;
		if(getText(locator).contains(validationtext))
			flag=true;
		return flag;
	}

	
	public void reporterLog(String status, String message) {
		if(status.equalsIgnoreCase("Pass") ||status.equalsIgnoreCase("Error") ||status.equalsIgnoreCase("Fatal")) {
			takeScreenshotFullPage(status, message);
			if(status.equalsIgnoreCase("Error"))
				new ProjectCustomException(getClassName(), getMethodName(), new Exception(), "Aborting the Test flow");
		}
		else {
			try {
				getReporter().log(Status.valueOf(status.toUpperCase()), message);
			}catch(Exception e) {
				new ProjectCustomException(getClassName(e), getMethodName(e), e, "Unable to get reporter object");
			}
		}
	}

	/**
	 * Function to load property file
	 * @param filePath
	 */
	public void loadPropertyFile(String filePath) {
		try {
			propFile = new Properties();
			propFile.load(new FileInputStream(new File(filePath)));
		}catch(Exception e) {
			new ProjectCustomException(getClassName(e), getMethodName(e), e, "Unable to load property File in th location '"+filePath+"'");
		}
	}

	
	public String getClassName(Exception exceptionMessage) {
		return exceptionMessage.getStackTrace()[0].getClassName();
	}

	
	public String getMethodName(Exception exceptionMessage) {
		return exceptionMessage.getStackTrace()[0].getMethodName();
	}

	
	public String getClassName() {
		return Thread.currentThread().getStackTrace()[1].getClassName();
	}

	
	public String getMethodName() {
		return Thread.currentThread().getStackTrace()[1].getMethodName();
	}
}
