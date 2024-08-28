package corp.ticket;

import java.sql.SQLException;
import java.util.List;

import corp.planet.Planet;

public interface ITicketCrudService {
    void save(Ticket ticket) throws SQLException;

    Ticket getById(String id) throws SQLException;

    List<Ticket> getAll() throws SQLException;

    long getTicketCountToPlanet(Planet planet) throws SQLException;

    void update(Ticket ticket) throws SQLException;

    void delete(Ticket ticket) throws SQLException;
}
