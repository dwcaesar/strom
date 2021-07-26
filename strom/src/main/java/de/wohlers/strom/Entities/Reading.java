package de.wohlers.strom.Entities;

import de.wohlers.strom.DAO.DAO;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Reading extends DAO {
    @Id
    private int    id;
    @ManyToOne (fetch = FetchType.LAZY)
    private Meter  meter;
    private Date   time;
    private double reading;

}
