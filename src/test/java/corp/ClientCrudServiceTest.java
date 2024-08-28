package corp;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;

import corp.storage.HibernateUtil;
import corp.storage.Storage;
import corp.client.IClientCrudService;
import corp.client.ClientCrudService;
import corp.client.Client;

import java.sql.SQLException;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientCrudServiceTest {
    private final IClientCrudService clientCrudService = new ClientCrudService();

    @BeforeAll
    void setup() {
        Storage.getInstance().getConnection();
        HibernateUtil.getInstance().getSessionFactory();
    }

    @Test
    void testSaveAndGetById() throws SQLException {
        Client client = new Client();
        client.setName("Test Client");

        clientCrudService.save(client);

        Client retrievedClient = clientCrudService.getById(client.getId());

        Assertions.assertNotNull(retrievedClient);
        Assertions.assertEquals("Test Client", retrievedClient.getName());

        clientCrudService.delete(client);
    }

    @Test
    void testGetAll() throws SQLException {
        Client client1 = new Client();
        client1.setName("Client 1");

        Client client2 = new Client();
        client2.setName("Client 2");

        clientCrudService.save(client1);
        clientCrudService.save(client2);

        List<Client> clients = clientCrudService.getAll();

        Assertions.assertNotNull(clients);
        Assertions.assertTrue(clients.size() >= 2);

        clientCrudService.delete(client1);
        clientCrudService.delete(client2);
    }

    @Test
    void testUpdate() throws SQLException {
        Client client = new Client();
        client.setName("Original Name");

        clientCrudService.save(client);

        client.setName("Updated Name");
        clientCrudService.update(client);

        Client updatedClient = clientCrudService.getById(client.getId());

        Assertions.assertNotNull(updatedClient);
        Assertions.assertEquals("Updated Name", updatedClient.getName());

        clientCrudService.delete(updatedClient);
    }

    @Test
    void testDelete() throws SQLException {
        Client client = new Client();
        client.setName("To Be Deleted");

        clientCrudService.save(client);

        Long clientId = client.getId();
        clientCrudService.delete(client);

        Client deletedClient = clientCrudService.getById(clientId);

        Assertions.assertNull(deletedClient);
    }
}

