package reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import tests.TestBase;
import utilities.reports.ReportiumReportManager;

public class ReportiumTestListener extends TestBase implements ITestListener {

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    	String tags = "Sanity";
    	try {
			if(System.getProperty("reportium-tags")!= null || (!System.getProperty("reportium-tags").equals(""))) {
				tags = System.getProperty("reportium-tags").toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	ReportiumReportManager.getReportiumClient().testStart(iTestResult.getMethod().getMethodName(), new TestContext(tags));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    	ReportiumReportManager.getReportiumClient().testStop(TestResultFactory.createSuccess());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
    	ReportiumReportManager.getReportiumClient().testStop(TestResultFactory.createFailure(iTestResult.getThrowable().getMessage(), iTestResult.getThrowable()));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }
}
