package de.wohlers.strom.billing;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class Fee {

    private long       id;
    private FeeType    feeType;
    private String     placeName;
    private Date       startDate = null;
    private Date       endDate   = null;
    private BigDecimal rate      = null;
    private BigDecimal total;

    public enum FeeType {
        BASIC_FEE, SETUP_FEE
    }
}
