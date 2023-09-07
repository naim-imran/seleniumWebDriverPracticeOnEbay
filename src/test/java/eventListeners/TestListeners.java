package eventListeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import seleniumWebDriverPracticeOnEbay.InitialComponents;

public class TestListeners extends InitialComponents implements ITestListener {

	private ExtentTest report;
	private ExtentReports extentReport;
	private ThreadLocal<ExtentTest> threadSafeLog;

	@Override
	public void onTestStart(ITestResult result) {
		report = extentReport.createTest(result.getMethod().getMethodName());
		threadSafeLog.set(report);
		System.out.println(result.getMethod().getMethodName() + " execution is started on " + result.getMethod().getId());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		threadSafeLog.get().log(Status.PASS, result.getMethod().getMethodName() + " execution is passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {

		threadSafeLog.get().fail(result.getThrowable());
	
		WebDriver driver = null;
		try {

			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());

		} catch (Throwable e) {
			e.printStackTrace();
		}

		
		  File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  String name = result.getMethod().getMethodName() +
		  getCurrentTimeToFormatedString();
		  
		  // copy the file using copyFile() method in FileUtils class. try {
		  try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") + "\\reportsAndScreenshoots\\" + name + ".png"));
			threadSafeLog.get().addScreenCaptureFromPath(System.getProperty("user.dir") + "\\reportsAndScreenshoots\\" + name + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		} }
				
	

	@Override
	public void onTestSkipped(ITestResult result) {
		threadSafeLog.get().log(Status.SKIP, "skiped");
		threadSafeLog.get().log(Status.SKIP, result.getThrowable());
	}

	

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		threadSafeLog = new ThreadLocal<ExtentTest>();
		extentReport = configureExtentReport();

	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
