package corp.ticket;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

import corp.client.Client;
import corp.planet.Planet;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_planet_id", referencedColumnName = "id", nullable = false)
    private Planet fromPlanet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_planet_id", referencedColumnName = "id", nullable = false)
    private Planet toPlanet;

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", createdAt=" + createdAt +
                ", fromPlanet=" + (fromPlanet != null ? fromPlanet.getName() : null) +
                ", toPlanet=" + (toPlanet != null ? toPlanet.getName() : null) +
                ", client=" + (client != null ? client.getName() : null) + "}";
    }
}

