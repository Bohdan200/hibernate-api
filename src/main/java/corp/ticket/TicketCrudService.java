package corp.ticket;

import corp.storage.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;
import corp.client.Client;
import corp.planet.Planet;


public class TicketCrudService implements ITicketCrudService {
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    @Override
    public void save(Ticket ticket) {
        if (ticket.getClient() == null ||
                ticket.getFromPlanet() == null ||
                ticket.getToPlanet() == null) {
            throw new IllegalArgumentException("Client and Planets cannot be null");
        }

        try (Session session = sessionFactory.openSession()) {
            Client existingClient = session.get(Client.class, ticket.getClient().getId());
            if (existingClient == null) {
                throw new IllegalArgumentException("Client does not exist");
            }

            Planet existingFromPlanet = session.get(Planet.class, ticket.getFromPlanet().getId());
            Planet existingToPlanet = session.get(Planet.class, ticket.getToPlanet().getId());

            if (existingFromPlanet == null || existingToPlanet == null) {
                throw new IllegalArgumentException("One or both planets do not exist");
            }

            Transaction tx = session.beginTransaction();
            session.persist(ticket);
            tx.commit();
        }
    }

    @Override
    public Ticket getById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        }
    }

    @Override
    public long getTicketCountToPlanet(Planet planet) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            NativeQuery<Long> query = session.createNativeQuery(
                    "SELECT count(*) FROM ticket WHERE to_planet_id = :to LIMIT 1",
                    Long.class
            );
            query.setParameter("to", planet.getId());

            return query.getSingleResult();
        }
    }

    @Override
    public void update(Ticket ticket) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(ticket);
            tx.commit();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(ticket);
            tx.commit();
        }
    }
}
