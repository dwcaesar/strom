package de.wohlers.strom.metering;

import de.wohlers.strom.administration.Place;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Reading {

    private int        id;
    private Date       timestamp;
    private Meter      meter;
    private BigDecimal reading;

    public static List<Reading> findReadingsForPlaceInPeriod(Place place, Date periodStartDate, Date periodEndDate) {
        return List.of(); // TODO
    }
}
