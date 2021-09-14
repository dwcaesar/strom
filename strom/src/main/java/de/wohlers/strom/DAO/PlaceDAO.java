package de.wohlers.strom.DAO;

import de.wohlers.strom.Models.Place;

import javax.persistence.EntityManager;
import java.util.List;

public class PlaceDAO extends DAO<Place> {
    private static PlaceDAO instance;

    public static PlaceDAO getInstance() {
        if(instance == null) {
            instance = new PlaceDAO();
        }
        return instance;
    }

    public List<Place> findAll() {
        EntityManager em = getManager();
        return em.createQuery("SELECT p FROM Place p", Place.class).getResultList();
    }
}
