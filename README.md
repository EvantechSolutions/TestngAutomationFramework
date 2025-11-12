🧪 Automation Testing Framework (TestNG + Selenium)
📘 Overview

This project is a Test Automation Framework built using Selenium WebDriver, Java, and TestNG.
It follows the Page Object Model (POM) design pattern with reusable utilities, reporting.
The goal of this framework is to provide a scalable, maintainable, and easily extendable foundation for automated web testing.

🚀 Features

✅ Built using Java + TestNG + Selenium WebDriver  
✅ Implements Page Object Model (POM) for better maintainability     
✅ Extent Reports / Allure Reports /Klov integration for reporting  
✅ TestNG Listeners for failure capture and report enhancement  
✅ Cross-browser support (Chrome, Firefox, Edge)  
✅ Eexecution via testng.xml  
✅ CI/CD ready (supports Jenkins, GitHub Actions)  
✅ Reusable Base Classes for setup and teardown  
✅ Data-driven testing (Excel / CSV / Property files)  

🧩 Project Structure

                      ├── src/
                      │   ├── main/
                      │   │   ├── java/
                      │   │   │   ├── com.framework.base/ 				# Base classes for setup, config, and test initialization
                      │   │   │   │   ├── DriverSetup.java
                      │   │   │   │   └── ConfigReader.java
                      │   │   │   │
                      │   │   │   ├── com.framework.pages/              	# Page Object Model (UI locators & actions)
                      │   │   │   │   ├── HomePage.java
                      │   │   │   │   ├── ContactUsPage.java
                      │   │   │   │   └── ServicesPage.java
                      │   │   │
                      │   │   └── resources/
                      │   │       └── testdata/
                      │   │           └── TestData.xlsx
                      │   │── test/
                      │   │   │── java/
                      │   │   │   ├── com.framework.Base/					# Base test initialization
                      │   │   │   │   ├── BaseApi.java
                      │   │   │   │   ├── BaseTest.java
                      │   │   │   ├── com.framework.tests.ui/		    	# UI automation test classes
                      │   │   │   │   ├── HomePageTest.java
                      │   │   │   │   ├── ContactUsTest.java
                      │   │   │   │   └── ServicesTest.java
                      │   │   │   │── com.framework.tests.api/      		# API test classes using Rest Assured
                      │   │   │   │     └── ApiTest.java
                      │   │   │   ├── com.framework.utils/                # Utility helper classes
                      │   │   │   │   ├── LoggerUtil.java
                      │   │   │   │   ├── ConfigLoader.java
                      │   │   │   │   ├── DataGenerator.java
                      │   │   │   │   ├── ExcelReader.java
                      │   │   │   │   ├── DBReader.java
                      │   │   │   │   ├──ExtentReportManager.java
                      │	│	│	│	└── TestListener.java
                      │
                      ├── reports/            							# Test execution reports (Extent/TestNG HTML)
                      │   └── ExtentReport.html
                      │
                      ├── screenshots/     								# Failed test screenshots
                      │
                      ├── Jenkinsfile     								# CI/CD integration for build and test execution
                      ├── pom.xml  										# Maven dependencies and build configuration
                      ├── ExtendReport.xml  									# TestNG suite
                      ├── TestSuite.xml
                      ├── KlovReport.xml
                      └── AllureReport.xml
⚙️ Prerequisites

Before running tests, ensure the following are installed:

| Tool                         | Version     | Description                           |
| ---------------------------- | ----------- | ------------------------------------- |
| **Java JDK**                 | 11 or above | Required to compile and run Java code |
| **Maven**                    | 3.8+        | Dependency management and build tool  |
| **TestNG**                   | Latest      | Testing framework                     |
| **Allure CLI** *(optional)*  | Latest      | For report generation                 |
| **Chrome / Firefox Drivers** | Latest      | For browser automation                |

🏗️ Setup Instructions

1.Clone the repository

git clone https://github.com/yourusername/automation-framework.git
cd automation-framework

2.Install dependencies
mvn clean install

3.Configure test settings
Update config.properties with environment details:
browser=chrome
baseUrl=https://example.com

🧪 How to Run Tests

1.Run from Command Line

Run all tests:

mvn clean test

Run specific suite:

mvn clean test -DsuiteXmlFile=testng.xml

2.Run from TestNG XML

Edit testng.xml and specify which tests or groups to run:

<suite name="Smoke Suite">
    <test name="Smoke Tests">
        <classes>
            <class name="tests.SmokeTest"/>
        </classes>
    </test>
</suite>


Then run:

mvn test -DsuiteXmlFile=src/test/resources/testng.xml

📊 Reports
🔹 Extent Report

After test execution, an HTML report will be generated at:

/reports/ExtentReportDate.html

🔹 Allure Report 

Generate Allure report:

allure serve allure-results

🧰 Utilities
| Utility            | Purpose                                     |
| ------------------ | ------------------------------------------- |
| **ConfigReader**   | Reads data from property files              |
| **ExcelUtil**      | Handles Excel-based data-driven tests       |
| **CSVUtil**        | Handles CSV-based data-driven tests         |
| **ExcelUtil**      | Handles Excel-based data-driven tests       |
| **DriverSetup**    | Initializes and manages WebDriver instances |
| **TestListener**   | Integrates logging and reporting hooks      |



