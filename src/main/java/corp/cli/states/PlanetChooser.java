package corp.cli.states;

import corp.planet.PlanetCrudService;
import lombok.RequiredArgsConstructor;
import corp.planet.Planet;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RequiredArgsConstructor
public class PlanetChooser {
    private final Scanner scanner;

    PlanetCrudService planetCrudService = new PlanetCrudService();

    public Planet ask() {
        while (true) {
            String line = scanner.nextLine();

            if (Objects.equals(line, "exit")) {
                System.exit(0);
            }

            try {
                List<Planet> planets = planetCrudService.getAll();
                for (Planet planet : planets) {
                    if (Objects.equals(planet.getName(), line)) {
                        return planet;
                    }
                }

                System.out.println("Planet " + line + " not found. Enter correct planet");
            } catch (Exception ex) {
                System.out.println("Error while fetching planets: " + ex.getMessage());
            }
        }
    }
}
