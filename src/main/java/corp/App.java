package corp;

import corp.storage.ConnectionProvider;

import corp.cli.CliFSM;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        ConnectionProvider connectionProvider = new ConnectionProvider();

        new CliFSM(connectionProvider);

        connectionProvider.close();
    }
}