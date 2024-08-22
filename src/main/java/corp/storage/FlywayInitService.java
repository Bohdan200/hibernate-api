package corp.storage;

import org.flywaydb.core.Flyway;

public class FlywayInitService {
    public void initDb(String dbUrl, String dbUser, String dbPass) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(dbUrl, dbUser, dbPass)
                .load();

        flyway.migrate();
    }
}
