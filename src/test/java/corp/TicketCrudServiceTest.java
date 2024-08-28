package corp;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;

import corp.storage.HibernateUtil;
import corp.storage.Storage;
import java.sql.SQLException;

import corp.ticket.ITicketCrudService;
import corp.client.IClientCrudService;
import corp.planet.IPlanetCrudService;
import corp.ticket.TicketCrudService;
import corp.client.ClientCrudService;
import corp.planet.PlanetCrudService;
import corp.client.Client;
import corp.planet.Planet;
import corp.ticket.Ticket;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketCrudServiceTest {
    private final ITicketCrudService ticketCrudService = new TicketCrudService();
    private final IClientCrudService clientCrudService = new ClientCrudService();
    private final IPlanetCrudService planetCrudService = new PlanetCrudService();
    private Client client;
    private Planet fromPlanet;
    private Planet toPlanet;

    @BeforeAll
    void setup() throws SQLException {
        Storage.getInstance().getConnection();
        HibernateUtil.getInstance().getSessionFactory();

        client = clientCrudService.getById(2L);
        fromPlanet = planetCrudService.getById("MARS");
        toPlanet = planetCrudService.getById("SAT");
    }

    Ticket createFullTicket() {
        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setFromPlanet(fromPlanet);
        ticket.setToPlanet(toPlanet);
        ticket.setCreatedAt(Timestamp.from(Instant.now()));

        return ticket;
    }

    @Test
    void testThatTicketSaved() throws SQLException {
        Ticket newTicket = createFullTicket();

        Assertions.assertDoesNotThrow(() -> ticketCrudService.save(newTicket));
        ticketCrudService.delete(newTicket);
    }

    @Test
    void testSaveValidTicket() throws SQLException {
        Ticket newTicket = createFullTicket();
        ticketCrudService.save(newTicket);

        Ticket createdTicket = ticketCrudService.getById(String.valueOf(newTicket.getId()));
        Assertions.assertNotNull(createdTicket, "The created ticket should not be null");

        String createdTicketName = createdTicket.getClient().getName();
        String createdTicketFromPlanet = createdTicket.getFromPlanet().getName();
        String createdTicketToPlanet = createdTicket.getToPlanet().getName();

        Assertions.assertEquals("Bob", createdTicketName, "The 'clientName' should be 'Bob'");
        Assertions.assertEquals("Mars", createdTicketFromPlanet, "The 'fromPlanet' should be 'Mars'");
        Assertions.assertEquals("Saturn", createdTicketToPlanet, "The 'toPlanet' should be 'Saturn'");

        ticketCrudService.delete(newTicket);
    }

    @Test
    void testSaveTicketWithNullClient() {
        Ticket newTicket = createFullTicket();
        newTicket.setClient(null);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ticketCrudService.save(newTicket));
        Assertions.assertEquals("Client and Planets cannot be null", exception.getMessage());
    }

    @Test
    void testSaveTicketWithNullFromPlanet() {
        Ticket newTicket = createFullTicket();
        newTicket.setFromPlanet(null);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ticketCrudService.save(newTicket));
        Assertions.assertEquals("Client and Planets cannot be null", exception.getMessage());
    }

    @Test
    void testSaveTicketWithNullToPlanet() {
        Ticket newTicket = createFullTicket();
        newTicket.setToPlanet(null);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ticketCrudService.save(newTicket));
        Assertions.assertEquals("Client and Planets cannot be null", exception.getMessage());
    }

    @Test
    void testGetById() throws SQLException {
        Ticket newTicket = createFullTicket();

        ticketCrudService.save(newTicket);
        Ticket retrievedTicket = ticketCrudService.getById(String.valueOf(newTicket.getId()));

        Assertions.assertNotNull(retrievedTicket);
        Assertions.assertEquals(newTicket.getId(), retrievedTicket.getId());

        ticketCrudService.delete(retrievedTicket);
    }

    @Test
    void testGetAll() throws SQLException {
        Ticket newTicket1 = createFullTicket();
        Ticket newTicket2 = createFullTicket();
        ticketCrudService.save(newTicket1);
        ticketCrudService.save(newTicket2);

        List<Ticket> tickets = ticketCrudService.getAll();
        Assertions.assertNotNull(tickets);
        Assertions.assertTrue(tickets.size() >= 12);

        ticketCrudService.delete(newTicket1);
        ticketCrudService.delete(newTicket2);
    }

    @Test
    void testUpdateTicket() throws SQLException {
        Ticket newTicket = createFullTicket();
        ticketCrudService.save(newTicket);

        Planet newToPlanet =  planetCrudService.getById("EARTH");
        newTicket.setToPlanet(newToPlanet);
        ticketCrudService.update(newTicket);

        Ticket updatedTicket = ticketCrudService.getById(String.valueOf(newTicket.getId()));
        String updatedTicketName = updatedTicket.getToPlanet().getName();

        Assertions.assertEquals("Earth", updatedTicketName, "The 'toPlanet' should be updated to 'Earth'");
        ticketCrudService.delete(updatedTicket);
    }

    @Test
    void testDeleteTicket() throws SQLException {
        Ticket newTicket = createFullTicket();

        ticketCrudService.save(newTicket);
        ticketCrudService.delete(newTicket);

        Ticket deletedTicket = ticketCrudService.getById(String.valueOf(newTicket.getId()));
        Assertions.assertNull(deletedTicket);
    }
}

