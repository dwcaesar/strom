package de.wohlers.strom.billing;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class Consumption {

    private long       id;
    private String     placeName;
    private String     meterName;
    private Date       startDate;
    private Date       endDate;
    private BigDecimal startReading;
    private BigDecimal endReading;
    private BigDecimal difference;
    private BigDecimal rate;
    private BigDecimal total;

}
