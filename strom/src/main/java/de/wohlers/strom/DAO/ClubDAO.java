package de.wohlers.strom.DAO;

import de.wohlers.strom.Models.Club;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ClubDAO extends DAO<Club> {

    private static ClubDAO instance;

    public static ClubDAO getInstance() {
        if (instance == null) {
            instance = new ClubDAO();
        }
        return instance;
    }

    public Club findClub() {
        Club          club;

        try {
            EntityManager em = getManager();
            club = em.createQuery("SELECT c FROM Club c", Club.class).getSingleResult();
        } catch (NoResultException e) {
            club = new Club();
            persist(club);
        }

        return club;
    }
}
