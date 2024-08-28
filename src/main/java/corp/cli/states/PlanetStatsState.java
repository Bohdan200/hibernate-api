package corp.cli.states;

import corp.cli.CliFSM;
import corp.cli.CliState;
import corp.planet.Planet;
import corp.ticket.ITicketCrudService;
import corp.ticket.TicketCrudService;

import java.sql.SQLException;

public class PlanetStatsState extends CliState {
    public PlanetStatsState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        try {
            ITicketCrudService ticketCrudService = new TicketCrudService();

            System.out.println("Enter TO planet:");

            Planet planet = new PlanetChooser(fsm.getScanner()).ask();

            long ticketCount = ticketCrudService.getTicketCountToPlanet(planet);
            System.out.println(planet + " found. Ticket count: " + ticketCount);

            fsm.setState(new IdleState(fsm));
        } catch (SQLException e) {
            System.out.println("Error while planet chose: " + e.getMessage());
        }
    }
}
