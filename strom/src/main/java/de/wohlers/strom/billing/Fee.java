package de.wohlers.strom.billing;

import de.wohlers.strom.administration.Conditions;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class Fee {

    /**
     * days * hours * minutes * seconds * millis
     */
    public static final double MILLIS_PER_MONTH = 30.5 * 24 * 60 * 60 * 1000;

    private long       id;
    private FeeType    feeType;
    private String     placeName;
    private Date       startDate = null;
    private Date       endDate   = null;
    private BigDecimal rate      = null;
    private BigDecimal total;

    public static Fee newSetupFee(Conditions conditions) {
        Fee out = new Fee();
        out.feeType = FeeType.SETUP_FEE;
        out.total = conditions.getInitialFee();
        return out;
    }

    public static Fee newBasic(String placeName, Conditions currentConditions, Date periodStartDate, Date periodEndDate) {
        double millisInPeriod = periodEndDate.getTime() - periodStartDate.getTime();
        double monthsInPeriod = Math.round(millisInPeriod / MILLIS_PER_MONTH);

        Fee out = new Fee();
        out.placeName = placeName;
        out.feeType = FeeType.BASIC_FEE;
        out.startDate = periodStartDate;
        out.endDate = periodEndDate;
        out.rate = currentConditions.getBasicFee();
        out.total = currentConditions.getBasicFee().multiply(BigDecimal.valueOf(monthsInPeriod));

        return out;
    }

    public enum FeeType {
        BASIC_FEE, SETUP_FEE
    }
}
