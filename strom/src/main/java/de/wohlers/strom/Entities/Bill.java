package de.wohlers.strom.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Bill extends Entity {
    @Id
    private int                id;
    @ManyToOne (fetch = FetchType.LAZY)
    private Period             period;
    @ManyToOne (fetch = FetchType.LAZY)
    private Contract           contract;
    private Date               startDate;
    private Date               endDate;
    private Date               dueDate;
    private Date               sendDate;
    private NotificationMethod usedNotificationMethod;
    @OneToMany (fetch = FetchType.LAZY)
    private List<Consumption>  consumptions;
    private double             powerRate;
    private double             basicFeeRate;
    private double             connectFee;
    private double             oldAdvance;
    private double             currentAdvance;
    private double             total;
    private boolean            directDebit;
}
