package de.wohlers.strom.Entities;

import de.wohlers.strom.DAO.DAO;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import java.util.Date;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Consumption extends DAO {
    @Id
    private int     id;
    private Date    startDate;
    private Date    endDate;
    private double  startReading;
    private double  endReading;
    private double  total;
    private boolean isEstimated;

}
