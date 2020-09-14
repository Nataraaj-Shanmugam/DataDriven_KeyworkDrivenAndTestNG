package com.utilities;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.genericKeywords.GenericKeywords;
import com.genericKeywords.ThreadLocalFunctionalities;

public class ReporterUtilities {

	private static ExtentReports extent;

	public void createReporterTest(String scenarioName) {
		ThreadLocalFunctionalities.extentReporter.set(extent.createTest(scenarioName));
	}

	public void createReporterFile(){
		extent = reportManager(GenericKeywords.reportPath+"\\TestReport.html");
	}

	public void writeDataToReport() {
		extent.flush();
	}

	public ExtentReports reportManager(String fileName) {
		ExtentSparkReporter obj = new ExtentSparkReporter(fileName);
		obj.config().setReportName("<b>"+"<font size=5>"+"Test Automation Report"+"</font>"+"</b>");
		obj.config().setDocumentTitle(fileName);
		obj.config().setTheme(Theme.DARK);
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(obj);
		extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
		extent.setSystemInfo("SDET Name:", GenericKeywords.propFile.getProperty("SDETName"));
		return extent;
	}


}
