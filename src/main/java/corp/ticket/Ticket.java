package corp.ticket;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column
    private long client_id;

    @Column
    private String from_planet_id;

    @Column
    private String to_planet_id;
}

