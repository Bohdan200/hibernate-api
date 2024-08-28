package corp.cli.states;

import corp.cli.CliFSM;
import corp.cli.CliState;
import corp.client.IClientCrudService;
import corp.client.ClientCrudService;
import corp.ticket.ITicketCrudService;
import corp.ticket.TicketCrudService;

import corp.client.Client;
import corp.ticket.Ticket;
import corp.planet.Planet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class AddTicketState extends CliState {
    public AddTicketState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        try {
            IClientCrudService clientCrudService = new ClientCrudService();

            System.out.println("Enter passenger id (passport):");

            String passport = fsm.getScanner().nextLine();

            if (Objects.equals(passport, "exit")) {
                System.exit(0);
            }

            Client client = clientCrudService.getById(Long.valueOf(passport));

            if (client != null) {
                System.out.println("Passenger " + client.getName() + " found. Enter FROM planet:");
            } else {
                System.out.println("Enter passenger name:");

                String clientName = fsm.getScanner().nextLine();

                client = new Client();
                client.setName(clientName);
                clientCrudService.save(client);

                client = clientCrudService.getById(client.getId());

                System.out.println("Passenger saved. Enter FROM planet:");
            }

            Planet fromPlanet = new PlanetChooser(fsm.getScanner()).ask();

            System.out.println(fromPlanet.getName() + " found. Enter TO planet");

            Planet toPlanet = new PlanetChooser(fsm.getScanner()).ask();

            ITicketCrudService ticketCrudService = new TicketCrudService();

            Ticket ticket = new Ticket();
            ticket.setClient(client);
            ticket.setFromPlanet(fromPlanet);
            ticket.setToPlanet(toPlanet);
            ticket.setCreatedAt(Timestamp.from(Instant.now()));
            ticketCrudService.save(ticket);

            long ticketId = ticket.getId();
            System.out.println(toPlanet.getName() + " found. Ticket ordered, ID: " + ticketId);

            fsm.setState(new IdleState(fsm));
        } catch (SQLException e) {
            System.out.println("Error while add ticket: " + e.getMessage());
        }
    }
}
