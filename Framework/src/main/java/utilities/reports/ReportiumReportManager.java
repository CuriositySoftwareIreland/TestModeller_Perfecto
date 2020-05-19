package utilities.reports;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

public class ReportiumReportManager {
    public static ReportiumClient reportiumClient;

    public static void createNewTest(Method m, WebDriver driver)
    {
        reportiumClient.testStart(m.getName(), new TestContext("Sanity"));
    }

    public static void initalise(WebDriver driver)
    {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("TestModeller project", "1.0"))
                .withWebDriver(driver)
                .build();

        reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }
}
