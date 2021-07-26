package de.wohlers.strom.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DAO<T> {

    private static EntityManagerFactory emf;
    private static EntityManager        em;

    protected static EntityManager getManager() {
        if (em == null) {
            EntityManagerFactory factory = getFactory();
            em = factory.createEntityManager();
        }
        return em;
    }

    private static EntityManagerFactory getFactory() {
        if (emf == null) { /* TODO - Speicherort der Datenbank soll im gleichen Verzeichnis sein, wie die jar selbst liegt. */
            emf = Persistence.createEntityManagerFactory("objectdb:$objectdb/db/strom.odb");
        }
        return emf;
    }

    public void persist(T member) {
        EntityManager em = getManager();
        em.getTransaction().begin();
        em.persist(member);
        em.getTransaction().commit();
    }

    public void remove(T member) {
        EntityManager em = getManager();
        em.getTransaction().begin();
        em.remove(member);
        em.getTransaction().commit();
    }

    public void merge(T member) {
        EntityManager em = getManager();
        em.getTransaction().begin();
        em.merge(member);
        em.getTransaction().commit();
    }

}
