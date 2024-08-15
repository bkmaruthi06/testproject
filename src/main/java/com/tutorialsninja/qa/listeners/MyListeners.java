package com.tutorialsninja.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorialsninja.qa.utils.ExtentReporter;
import com.tutorialsninja.qa.utils.utilities;

public class MyListeners implements ITestListener{
	
	ExtentReports extentReport;
	ExtentTest extentTest;
	String testName;
	
	@Override
	public void onStart(ITestContext context) {
		
		extentReport =  ExtentReporter.generateExtentReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		testName = result.getName();
		ExtentTest extentTest = extentReport.createTest(testName);
		extentTest.log(Status.INFO, testName+"  Started Executing  ");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest = extentReport.createTest(testName);
		extentTest.log(Status.PASS, testName+"  Got Successfully Executed  ");	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = null;
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String  destinationScreenshotPath = utilities.captureScreenshot(driver, result.getName());

		extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.FAIL,testName+" Got Failed  ");
		System.out.println(testName+"  Got Failed  ");
		System.out.println(result.getThrowable());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.SKIP,testName+" Got Skipped  ");		

	}



	@Override
	public void onFinish(ITestContext context) {

		extentReport.flush();
		
		String pathofExtentReport = System.getProperty("user.dir")+"\\TutorialsNinjaProj\\extentReportFile\\Index.html";
		File extentReport = new File(pathofExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
