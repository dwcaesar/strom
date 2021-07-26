package de.wohlers.strom.Models;

import javax.persistence.*;

@Entity
@Access (AccessType.FIELD)
public class Club {
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
