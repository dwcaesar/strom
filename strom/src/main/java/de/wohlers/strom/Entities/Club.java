package de.wohlers.strom.Entities;

import javax.persistence.*;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Club extends Entity {
    @Id
    private int    id;
    private String name;
    private String street;
    private String zip;
    private String city;
    private double basicFeeRate;
    private double connectFee;
    private double powerRate;
    @ManyToOne (fetch = FetchType.LAZY)
    private Member contact;
    @ManyToOne (fetch = FetchType.LAZY)
    private Member treasurer;

}
