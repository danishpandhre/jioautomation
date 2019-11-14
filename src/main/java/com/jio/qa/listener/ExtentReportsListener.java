package com.jio.qa.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.jio.qa.base.TestBase;
import com.jio.qa.utils.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportsListener extends TestBase implements ITestListener {
	protected ExtentReports extentReports;
	protected ExtentTest test;
	
	public void onTestStart(ITestResult result) {
		test = extentReports.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, result.getMethod().getMethodName() + " Test is Started");
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getMethod().getMethodName() + " Test is Passed");
	}

	public void onTestFailure(ITestResult result) {
		try {
			String screenShotPath = TestUtils.takeScreenshotAtEndOfTest();
			String file = test.addScreenCapture(screenShotPath);
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + " Test is Failed", file);
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + " Test is failed", result.getThrowable());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, result.getMethod().getMethodName() + " Test is Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
			extentReports = new ExtentReports(outputDirectory,
					false);
	}

	public void onFinish(ITestContext context) {
		extentReports.endTest(test);
		extentReports.flush();
		//extentReports.close();
	}
}
