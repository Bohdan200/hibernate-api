package corp.cli;

import corp.cli.states.IdleState;
import lombok.Getter;
import java.util.Scanner;

import corp.storage.ConnectionProvider;

public class CliFSM {
    private CliState state;

    @Getter
    private Scanner scanner;

    @Getter
    private ConnectionProvider connectionProvider;

    public CliFSM(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;

        state = new IdleState(this);

        scanner = new Scanner(System.in);

        startInputLoop();
    }

    private void startInputLoop() {
        while (true) {
            String command = scanner.nextLine();
            switch (command) {
                case "exit":
                    System.exit(0);
                    break;
                case "addTicket":
                    newTicketRequested();
                    break;
                case "planetStats":
                    planetStatsRequested();
                    break;
                default:
                    unknownCommand(command);
            }
        }
    }

    public void newTicketRequested() {
        state.newTicketRequested();
    }

    public void planetStatsRequested() {
        state.planetStatsRequested();
    }

    public void unknownCommand(String cmd) {
        state.unknownCommand(cmd);
    }

    public void setState(CliState state) {
        this.state = state;

        state.init();
    }
}
