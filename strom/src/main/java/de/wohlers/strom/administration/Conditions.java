package de.wohlers.strom.administration;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Conditions {

    private int        id;
    private Date       validFrom;
    /**
     * price in Euro per kWh
     */
    private BigDecimal energyPrice;
    /**
     * price in Euro per month
     */
    private BigDecimal basicFee;
    /**
     * price in Euro for first contract
     */
    private BigDecimal initialFee;

}
