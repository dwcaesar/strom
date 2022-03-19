package de.wohlers.strom.administration;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    /**
     * @return conditions that are valid at the given time
     */
    public static Conditions findCurrentConditions(Date creationDate) {
        return null; // TODO
    }

    /**
     * @return conditions that are valid in the given period between startDate and endDate
     */
    public static List<Conditions> findConditionsInTimePeriod(Date startDate, Date endDate) {
        return List.of(); // TODO
    }
}
