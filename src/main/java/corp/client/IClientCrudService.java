package corp.client;

import java.sql.SQLException;
import java.util.List;

public interface IClientCrudService {
    void save(Client client) throws SQLException;

    Client getById(Long id) throws SQLException;

    List<Client> getAll() throws SQLException;

    void update(Client client) throws SQLException;

    void delete(Client client) throws SQLException;
}
