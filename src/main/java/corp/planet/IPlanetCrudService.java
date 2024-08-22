package corp.planet;

import corp.planet.Planet;

import java.sql.SQLException;
import java.util.List;

public interface IPlanetCrudService {
    void save(Planet planet) throws SQLException;

    Planet getById(String id) throws SQLException;

    List<Planet> getAll() throws SQLException;

    void update(Planet planet) throws SQLException;

    void delete(Planet planet) throws SQLException;
}
