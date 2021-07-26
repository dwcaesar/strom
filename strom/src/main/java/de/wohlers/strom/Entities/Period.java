package de.wohlers.strom.Entities;

import de.wohlers.strom.DAO.DAO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Period extends DAO {
    @Id
    private int        id;
    private String     name;
    private Date       startDate;
    private Date       endDate;
    @OneToMany (mappedBy = "period", fetch = FetchType.LAZY)
    private List<Bill> bills;

    public static List<Period> findAll() {
        return getManager().createQuery("SELECT p FROM Period p", Period.class).getResultList();
    }

    public static Period findCurrent() {
        Date date = new Date();

        return getManager()
                .createQuery("SELECT p FROM Period p WHERE p.startDate <= :date AND p.endDate >= :date", Period.class)
                .setParameter("date", date)
                .getSingleResult();
    }

}
