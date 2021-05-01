package de.wohlers.strom.Entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class Entity {

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

}
