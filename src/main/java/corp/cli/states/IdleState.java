package corp.cli.states;

import corp.cli.CliFSM;
import corp.cli.CliState;

public class IdleState extends CliState {
    public IdleState(CliFSM fsm) {
        super(fsm);
        System.out.println("Enter your command: addTicket; planetStats; exit");
    }

    @Override
    public void unknownCommand(String cmd) {
        System.out.println("Unknown command: " + cmd);
        System.out.println("Enter your command: addTicket; planetStats; exit");
    }

    @Override
    public void newTicketRequested() {
        fsm.setState(new AddTicketState(fsm));
    }

    @Override
    public void planetStatsRequested() {
        fsm.setState(new PlanetStatsState(fsm));
    }
}
