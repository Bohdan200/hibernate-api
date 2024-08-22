package corp;

import corp.storage.Storage;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import corp.storage.HibernateUtil;
import corp.planet.PlanetCrudService;
import corp.planet.Planet;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetCrudServiceTest {
    private PlanetCrudService planetCrudService;

    @BeforeAll
    void setup() {
        Storage.getInstance().getConnection();
        HibernateUtil.getInstance().getSessionFactory();
        planetCrudService = new PlanetCrudService();
    }

    @AfterAll
    void close() {
        HibernateUtil.getInstance().close();
    }

    @Test
    void testSaveAndGetById() {
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
    void testGetAll() {
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
    void testUpdate() {
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
    void testDelete() {
        Planet planet = new Planet();
        planet.setId("NEP");
        planet.setName("Neptune");

        planetCrudService.save(planet);

        String planetId = planet.getId();
        planetCrudService.delete(planet);

        Planet deletedPlanet = planetCrudService.getById(planetId);

        Assertions.assertNull(deletedPlanet);
    }

    @Test
    void testInvalidId() {
        Planet invalidPlanet = new Planet();
        invalidPlanet.setId("invalid_id");
        invalidPlanet.setName("Invalid");

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            planetCrudService.save(invalidPlanet);
        });

        String expectedMessage = "could not execute statement";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
