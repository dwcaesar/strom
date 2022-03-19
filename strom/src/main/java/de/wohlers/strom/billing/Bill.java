package de.wohlers.strom.billing;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
public class Bill {

    private long              id;
    private Date              creationDate;
    private TimePeriod        timePeriod;
    private Member            recipient;
    private Club              club;
    private List<Consumption> consumptions;
    private List<Fee>         fees;
    private BigDecimal        total;
}
