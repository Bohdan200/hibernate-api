package corp.planet;

import jakarta.persistence.*;
import lombok.Data;

import corp.ticket.Ticket;
import java.util.List;

@Table(name = "planet")
@Entity
@Data
public class Planet {
    @Id
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @OneToMany(mappedBy = "fromPlanet")
    private List<Ticket> ticketsFrom;

    @OneToMany(mappedBy = "toPlanet")
    private List<Ticket> ticketsTo;
}