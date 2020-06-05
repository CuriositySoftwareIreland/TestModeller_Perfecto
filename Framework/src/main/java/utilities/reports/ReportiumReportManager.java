package utilities.reports;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import java.lang.reflect.Method;

public class ReportiumReportManager {
	
    private static final ThreadLocal<ReportiumClient> reportThreadLocal = new ThreadLocal<ReportiumClient>();
    
    
    public static void setReportiumClinet(ReportiumClient report) {
		reportThreadLocal.set(report);
	}
	
	public static ReportiumClient getReportiumClient() {
		return reportThreadLocal.get();
	}

//    public static void createNewTest(Method m, WebDriver driver)
//    {
//        reportiumClient.testStart(m.getName(), new TestContext("Sanity"));
//    }

    public void initalise(WebDriver driver, ITestContext test)
    {    	
    	PerfectoExecutionContext perfectoExecutionContext = null;
		if(System.getProperty("reportium-job-name")!=null)
		{
			String branch = System.getProperty("reportium-job-branch") == null ? "master" : System.getProperty("reportium-job-branch");
			perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
					.withProject(new Project("Automation Project", "1.0"))
					.withJob(new Job(System.getProperty("reportium-job-name"), Integer.parseInt(System.getProperty("reportium-job-number"))).withBranch(branch))
					.withWebDriver(driver)
					.build();
		}
		else {
			perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
					.withProject(new Project("Automation Project", "1.0"))
					.withWebDriver(driver)
					.build();
		}
		
		setReportiumClinet(new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext));
    }
}
