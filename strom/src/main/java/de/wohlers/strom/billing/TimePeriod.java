package de.wohlers.strom.billing;

import lombok.Getter;

import java.util.Date;

@Getter
public class TimePeriod {

    private final Date startDate;
    private final Date endDate;

    public TimePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
