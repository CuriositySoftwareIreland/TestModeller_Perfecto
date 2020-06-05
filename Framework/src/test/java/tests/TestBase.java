package tests;

import utilities.CapabilityLoader;
import utilities.PropertiesLoader;
import utilities.reports.ExtentReportManager;

import utilities.reports.ReportiumReportManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import ie.curiositysoftware.allocation.dto.AllocationType;
import ie.curiositysoftware.allocation.engine.DataAllocation;
import ie.curiositysoftware.allocation.engine.DataAllocationEngine;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;


public class TestBase {
    /********** Replace with your own details ***********/
    
    private Method method;

    protected ConnectionProfile cp = new ConnectionProfile(PropertiesLoader.getProperties().getProperty("testModeller.apiHost"), PropertiesLoader.getProperties().getProperty("testModeller.apiKey"));

    protected DataAllocationEngine dataAllocationEngine = new DataAllocationEngine(cp);

    @BeforeSuite(alwaysRun = true)
    public void setupReporter()
    {
        ExtentReportManager.setupReporter();
    }

    @BeforeSuite(alwaysRun = true)
    public void allocateData(ITestContext testContext)
    {
        // Create a list of all the pools that need allocating
        List<AllocationType> allocationTypes = new ArrayList<AllocationType>();

        ITestNGMethod[] methods =  testContext.getAllTestMethods();
        try {

            for (int i = 0; i < methods.length; i++) {
                ITestNGMethod method = methods[i];

                Method testMethod = method.getConstructorOrMethod().getMethod();

                if (testMethod != null && testMethod.isAnnotationPresent(DataAllocation.class))
                {
                    DataAllocation dataAllocation = testMethod.getAnnotation(DataAllocation.class);

                    for (String testType : dataAllocation.groups()) {
                        AllocationType allocationType = new AllocationType(dataAllocation.poolName(), dataAllocation.suiteName(), testType);

                        allocationTypes.add(allocationType);
                    }
                }
            }
        } catch (Throwable e) {
            System.err.println(e);
        }

        // Publish and allocate data
        if (!dataAllocationEngine.resolvePools(PropertiesLoader.getProperties().getProperty("testModeller.serverName"), allocationTypes)) {
            System.out.println("Error - " + dataAllocationEngine.getErrorMessage());
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void initDriver(ITestContext test) throws Exception
    {
        ExtentReportManager.createNewTest(method);
        new CapabilityLoader().createWebDriver(test);
        if((!test.getCurrentXmlTest().getParameter("platformName").equalsIgnoreCase("ios"))&&(!test.getCurrentXmlTest().getParameter("platformName").equalsIgnoreCase("android"))) {
        	CapabilityLoader.getDriver().manage().window().maximize();
        }
        new ReportiumReportManager().initalise(CapabilityLoader.getDriver(), test);
        
    }
    
    @BeforeMethod(alwaysRun=true)
    public void getMethodName(Method method) {
		this.method = method;
	}

    @AfterMethod(alwaysRun = true)
    public void closerDriver(ITestResult result)
    {
//    	if(result.isSuccess()) {
//    		ReportiumReportManager.getReportiumClient().testStop(TestResultFactory.createSuccess());
//    	}else {
//    		ReportiumReportManager.getReportiumClient().testStop(TestResultFactory.createFailure(result.getThrowable().toString(), result.getThrowable(), ""));
//    	}
        CapabilityLoader.getDriver().quit();
    }

    @AfterSuite
    public void closeReporter()
    {
        ExtentReportManager.closeReporter();
    }
}
