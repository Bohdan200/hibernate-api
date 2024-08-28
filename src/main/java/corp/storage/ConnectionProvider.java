package corp.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionProvider {
    private final List<Connection> connections;

    public ConnectionProvider() {
        connections = new ArrayList<>();
    }

    public Connection createConnection() throws SQLException {
        Connection connection = Storage.getInstance().getConnection();

        connections.add(connection);

        return connection;
    }

    public void close() throws SQLException {
        for (Connection connection : connections) {
            if (connection.isClosed()) {
                continue;
            }

            connection.close();
        }
    }
}
