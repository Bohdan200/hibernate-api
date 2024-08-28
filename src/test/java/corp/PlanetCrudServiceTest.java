package corp;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;

import corp.storage.HibernateUtil;
import corp.storage.Storage;
import corp.planet.IPlanetCrudService;
import corp.planet.PlanetCrudService;
import corp.planet.Planet;

import java.sql.SQLException;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetCrudServiceTest {
    private final IPlanetCrudService planetCrudService = new PlanetCrudService();

    @BeforeAll
    void setup() {
        Storage.getInstance().getConnection();
        HibernateUtil.getInstance().getSessionFactory();
    }

    @Test
    void testSaveAndGetById() throws SQLException {
        Planet planet = new Planet();
        planet.setId("PLUTO");
        planet.setName("Pluto");

        planetCrudService.save(planet);

        Planet currentPlanet = planetCrudService.getById(planet.getId());

        Assertions.assertNotNull(currentPlanet);
        Assertions.assertEquals("PLUTO", currentPlanet.getId());
        Assertions.assertEquals("Pluto", currentPlanet.getName());

        planetCrudService.delete(planet);
    }

    @Test
    void testInvalidId() {
        Planet invalidPlanet = new Planet();
        invalidPlanet.setId("invalid_id");
        invalidPlanet.setName("Invalid");

        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            planetCrudService.save(invalidPlanet);
        });

        String actualMessage = exception.getMessage();
        String expectedMessage = "could not execute statement";

        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                "Expected a could not execute statement, but got: " + exception.getMessage());
    }

    @Test
    void testGetAll() throws SQLException {
        Planet planet1 = new Planet();
        planet1.setId("MERC");
        planet1.setName("Mercury");

        Planet planet2 = new Planet();
        planet2.setId("PLUTO");
        planet2.setName("Pluto");

        planetCrudService.save(planet1);
        planetCrudService.save(planet2);

        List<Planet> planets = planetCrudService.getAll();

        Assertions.assertNotNull(planets);
        Assertions.assertTrue(planets.size() >= 2);

        planetCrudService.delete(planet1);
        planetCrudService.delete(planet2);
    }

    @Test
    void testUpdate() throws SQLException {
        Planet planet = new Planet();
        planet.setId("SAT4");
        planet.setName("Saturn4");

        planetCrudService.save(planet);

        planet.setName("Updated Saturn");
        planetCrudService.update(planet);

        Planet updatedPlanet = planetCrudService.getById(planet.getId());

        Assertions.assertNotNull(updatedPlanet);
        Assertions.assertEquals("Updated Saturn", updatedPlanet.getName());

        planetCrudService.delete(updatedPlanet);
    }

    @Test
    void testDelete() throws SQLException {
        Planet planet = new Planet();
        planet.setId("NEP");
        planet.setName("Neptune");

        planetCrudService.save(planet);

        String planetId = planet.getId();
        planetCrudService.delete(planet);

        Planet deletedPlanet = planetCrudService.getById(planetId);

        Assertions.assertNull(deletedPlanet);
    }

}
