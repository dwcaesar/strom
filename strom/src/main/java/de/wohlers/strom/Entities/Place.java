package de.wohlers.strom.Entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Place extends Entity {
    @Id
    private int    id;
    private String name;

    public static List<Place> findAll() {
        return getManager().createQuery("SELECT p FROM Place p", Place.class).getResultList();
    }
}
