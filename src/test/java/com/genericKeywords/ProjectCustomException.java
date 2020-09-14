package com.genericKeywords;

import com.utilities.ReporterUtilities;

@SuppressWarnings("serial")
public class ProjectCustomException extends Exception {
	@SuppressWarnings("removal")
	public ProjectCustomException(String failedClassName , String failedMethodName , Exception exceptionDetails,String message) {
		super();
		if(ThreadLocalFunctionalities.getdriver()!=null)
			ThreadLocalFunctionalities.getdriver().quit();
		if(ThreadLocalFunctionalities.getReporter() == null)
			new ReporterUtilities().createReporterTest("Unable to find Extent Reporter Object");
		ThreadLocalFunctionalities.getReporter().fail("Failed in Class file: "+failedClassName+"</br>Failed in Method: "+failedMethodName+"</br>Exception Details: "+exceptionDetails+"</br>Message: "+message);
		Thread.currentThread().suspend();
	}
}
