package de.wohlers.strom.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Contract extends Entity {
    @Id
    private int        id;
    @ManyToOne (fetch = FetchType.LAZY)
    private Place      place;
    @ManyToOne (fetch = FetchType.LAZY)
    private Member     member;
    private Date       startDate;
    private Date       endDate;
    @OneToMany (mappedBy = "contract", fetch = FetchType.LAZY)
    private List<Bill> bills;

}
