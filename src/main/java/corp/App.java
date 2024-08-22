package corp;

import corp.client.Client;
import corp.client.ClientCrudService;
import corp.storage.Storage;
import java.sql.Connection;

public class App {
    static ClientCrudService clientCrudService = new ClientCrudService();
    public static void main(String[] args) {

        Connection connection = Storage.getInstance().getConnection();

        Client firstClient = new Client();
        firstClient.setName("George");
        clientCrudService.save(firstClient);

        Client secondClient = new Client();
        secondClient.setName("Mike");
        clientCrudService.save(secondClient);

        System.out.println("firstClient in database = " + clientCrudService.getById(firstClient.getId()).toString());
        System.out.println("secondClient in database = " + clientCrudService.getById(secondClient.getId()).toString());

        firstClient.setName("Updated Name");
        clientCrudService.update(firstClient);
        Client updatedClient = clientCrudService.getById(firstClient.getId());
        System.out.println("updated client in database = " + updatedClient.toString());

        clientCrudService.delete(updatedClient);
        clientCrudService.delete(secondClient);

        clientCrudService.getAll().forEach(client -> System.out.println(client.toString()));

    }
}