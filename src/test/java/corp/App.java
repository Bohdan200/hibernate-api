package corp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import corp.storage.HibernateUtil;
import java.io.PrintWriter;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class App {

    @AfterAll
    void close() {
        HibernateUtil.getInstance().close();
    }

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                        .selectors(
                                DiscoverySelectors.selectClass(ClientCrudServiceTest.class),
                                DiscoverySelectors.selectClass(PlanetCrudServiceTest.class),
                                DiscoverySelectors.selectClass(TicketCrudServiceTest.class)
                        )
                        .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));

        System.out.println("Successfully completed tests in quantity = " + summary.getTestsSucceededCount());

        summary.getFailures().forEach(failure -> {
            System.out.println("Test failed: " + failure.getTestIdentifier().getDisplayName());
            System.out.println("Reason: " + failure.getException().getMessage());
        });
    }
}
