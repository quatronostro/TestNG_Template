package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TestBaseReport {
    protected static ExtentReports extentReports; //Makes the initial assignment to the extent report
    protected static ExtentTest extentTest; // It records information such as test pass or failed. We also use it for screenshot
    protected static ExtentHtmlReporter extentHtmlReporter; // Organizes html report
    // Just before starting the test (not before the test method, but before the whole test operation)
    @BeforeTest(alwaysRun = true) // alwaysRun
    public void setUpTest() {
        extentReports = new ExtentReports(); // Starts reporting
        //After the report is created, you write where you want your report to be added here.
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/test-output/Rapor"+date+".html";
        //We start the report (in html format) we want to create, we specify the file path with filePath.
        extentHtmlReporter = new ExtentHtmlReporter(filePath);
        extentReports.attachReporter(extentHtmlReporter);
        // You can add the information you want here.
        extentReports.setSystemInfo("Enviroment","QA");
        extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser")); // which browser is selected on configuration.properties
        extentReports.setSystemInfo("Test Automation Engineer", "Baramuk");
        extentHtmlReporter.config().setDocumentTitle("TestNG Test");
        extentHtmlReporter.config().setReportName("TestNG Reports");
    }
    // After each test method, if there is an error in the test, it takes a screenshot and adds it to the report
    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) { // if the result of the test is failed
            String screenshotLocation = ReusableMethods.getScreenshot(result.getName());
            extentTest.fail(result.getName());
            extentTest.addScreenCaptureFromPath(screenshotLocation);
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) { // if the test is not passed without running
            extentTest.skip("Test Case is skipped: " + result.getName()); // Ignore ones
        }
        Driver.closeDriver();
    }
    // To end reporting
    @AfterTest(alwaysRun = true)
    public void tearDownTest() {
        extentReports.flush();
    }
}
