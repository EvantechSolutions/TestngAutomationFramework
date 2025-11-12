package Utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.framework.base.BaseApi;
import com.framework.base.DriverSetup;

public class TestListener implements ITestListener {
	private Map<String, Long> startTimes = new HashMap<>();

	private String captureScreenshot(WebDriver driver, String testName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String dir = System.getProperty("user.dir") + "/screenshots";
			File folder = new File(dir);
			if (!folder.exists())
				folder.mkdir();
			String dest = dir + "/" + testName + "_" + System.currentTimeMillis() + ".png";
			File finalDest = new File(dest);
			FileUtils.copyFile(source, finalDest);
			return dest;
		} catch (Exception e) {
			if (!new BaseApi().isApitest) {
				ExtentReportManager.getTest().log(Status.INFO, "API Testing No Screenshot");
			} else {
				ExtentReportManager.getTest().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
			}
			return null;
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		startTimes.put(methodName, System.currentTimeMillis());
		ExtentReportManager.createTest(methodName).log(Status.INFO, "Test Started: " + methodName);
		ExtentReportManager.getTest().log(Status.INFO, "Current running method: " + methodName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		long duration = System.currentTimeMillis() - startTimes.get(methodName);
		WebDriver driver = DriverSetup.getDriver();
		String screenshotPath = captureScreenshot(driver, methodName);
		ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
		ExtentReportManager.getTest().log(Status.INFO, "Duration: " + duration + " ms");
		ExtentReportManager.getTest().log(Status.INFO, "Moving to next method...");
		if (screenshotPath != null && !(new BaseApi().isApitest)) {
			try {
				ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				ExtentReportManager.getTest().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
			}
		} else {
			ExtentReportManager.getTest().log(Status.INFO, "No Screenshot for API Validation..");
		}
	}
	@Override
	public void onTestSkipped(ITestResult result) {
	    String methodName = result.getMethod().getMethodName();
	    WebDriver driver = DriverSetup.getDriver();
	    String screenshotPath = captureScreenshot(driver, methodName);

	    ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
	    ExtentReportManager.getTest().log(Status.INFO, "Reason: " + result.getThrowable().getMessage());
	    
	    if (screenshotPath != null && !(new BaseApi().isApitest)) {
	        try {
	            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
	        } catch (Exception e) {
	            ExtentReportManager.getTest().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
	        }
	    } else {
	        ExtentReportManager.getTest().log(Status.INFO, "No Screenshot for skipped API test..");
	    }
	}


	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		long duration = System.currentTimeMillis() - startTimes.get(methodName);
		WebDriver driver = DriverSetup.getDriver();
		String screenshotPath = captureScreenshot(driver, methodName);
		ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
		ExtentReportManager.getTest().log(Status.INFO, "Duration: " + duration + " ms");
		ExtentReportManager.getTest().log(Status.INFO, "Moving to next Test method...");
		if (screenshotPath != null) {
			try {
				ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				ExtentReportManager.getTest().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
			}
		}
	}
	

	@Override
	public void onFinish(ITestContext context) {
		int passed = context.getPassedTests().size();
		int failed = context.getFailedTests().size();
		int skipped = context.getSkippedTests().size();
		int total = passed + failed + skipped;
		double passPercentage = (total > 0) ? ((passed * 100.0) / total) : 0.0;
		String suiteName = context.getSuite().getName();
		String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		ExtentReportManager.createTest("Suite Summary: " + suiteName).log(Status.INFO, " Date: " + today)
				.log(Status.INFO, "Total Tests: " + total).log(Status.INFO, " Passed: " + passed)
				.log(Status.INFO, " Failed: " + failed).log(Status.INFO, " Skipped: " + skipped)
				.log(Status.INFO, "Pass Percentage: " + String.format("%.2f", passPercentage) + "%");
		System.out.println("Pass Percentage: " + passPercentage);
		ExtentReportManager.flush();
		String htmlSnippet = "";
		if (passPercentage == 100) {
			htmlSnippet = "<div id='celebration' style='position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:9999;'></div>"
					+ "<script src='https://cdn.jsdelivr.net/npm/canvas-confetti@1.5.1/dist/confetti.browser.min.js'></script>"
					+ "<script>confetti({particleCount:400,spread:460});</script></div>";
//					+ "<div style='text-align:center;bottom:10%;font-size:28px;color:green;font-weight:bold;'> Perfect Run! All tests passed successfully!</div>";
		} else if (passPercentage >= 80) {
//			htmlSnippet = "<div style='text-align:center;bottom:10%;font-size:24px;color:orange;'> Great Job! "
//					+ String.format("%.2f", passPercentage) + "% tests passed. Keep improving!</div>";
		} else {
//			htmlSnippet = "<div style='text-align:center;bottom:10%;font-size:24px;color:red;'> Only "
//					+ String.format("%.2f", passPercentage) + "% passed. Please review failed tests!</div>";
		}
		try {
			File reportsDir = new File("reports");
			File[] files = reportsDir.listFiles((dir, name) -> name.endsWith(".html"));
			if (files != null && files.length > 0) {
				File latestReport = files[0];
				for (File f : files) {
					if (f.lastModified() > latestReport.lastModified()) {
						latestReport = f;
					}
				}
				String content = new String(Files.readAllBytes(latestReport.toPath()));
				content = content.replace("</body>", htmlSnippet + "\n</body>");
				Files.write(latestReport.toPath(), content.getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.TRUNCATE_EXISTING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}