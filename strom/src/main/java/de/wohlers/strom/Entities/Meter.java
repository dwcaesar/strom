package de.wohlers.strom.Entities;

import de.wohlers.strom.DAO.DAO;
import de.wohlers.strom.Models.Place;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Meter extends DAO<Meter> {
    @Id
    private int           id;
    private String serialNumber;
    @ManyToOne (fetch = FetchType.LAZY)
    private Place  place;
    private Date   installDate;
    private Date          removeDate;
    @OneToMany (mappedBy = "meter", fetch = FetchType.LAZY)
    private List<Reading> readings;
}
