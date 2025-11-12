package Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentReports getExtent() {
		if (extent == null) {
			extent = new ExtentReports();
			File reportDir = new File("reports");
			if (!reportDir.exists())
				reportDir.mkdir();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
			String timestamp = LocalDateTime.now().format(dtf);
			String reportPath = "reports/ExtentReport_" + timestamp + ".html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			try {
				String customCss = readCSSFile("src/main/resources/CSS.css");
				spark.config().setCss(customCss);
			} catch (IOException e) {
				e.printStackTrace();
			}
			spark.config().setReportName("Regression Report");
			spark.config().setDocumentTitle("Automation Test Results");
			try {
				ExtentKlovReporter klov = new ExtentKlovReporter("Automation Project");
				klov.initMongoDbConnection("localhost", 27017);
				klov.setProjectName("Klov Automation Project");
				klov.setReportName("Test Execution - " + dtf);
				klov.initKlovServerConnection("http://localhost:8085");
				extent.attachReporter(spark,klov);
			} catch (Exception e) {
				System.out.println("Klov server not available. Proceeding without Klov reporting.");
				extent.attachReporter(spark);
				extent.setSystemInfo("Environment", "Staging");
				extent.setSystemInfo("Browser", "Chrome 141");
				extent.setSystemInfo("OS", "Windows 11");
				extent.setSystemInfo("Executed By", "Prem");
			}
//         extent.attachReporter(spark,klov);
		}
		return extent;
	}

	private static String readCSSFile(String filePath) throws IOException {
		java.nio.file.Path path = Paths.get(filePath);
		return Files.readString(path);
	}

	public static ExtentTest createTest(String name) {
		ExtentTest extentTest = getExtent().createTest(name);
		test.set(extentTest);
		return extentTest;
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void flush() {
		getExtent().flush();
	}
}
