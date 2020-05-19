package reports;

import com.aventstack.extentreports.Status;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.TestBase;
import utilities.reports.ExtentReportManager;
import utilities.reports.ReportiumReportManager;

public class ReportiumTestListener extends TestBase implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {


    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ReportiumReportManager.reportiumClient.testStop(TestResultFactory.createSuccess());

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ReportiumReportManager.reportiumClient.testStop(TestResultFactory.createFailure(iTestResult.getThrowable().toString(), iTestResult.getThrowable(), ""));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }
}
