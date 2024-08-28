package corp.client;

import corp.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "client")
@Entity
@Data
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return "Client{id=" + id + ", name='" + name + "'}";
    }
}
