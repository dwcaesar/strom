package de.wohlers.strom.billing;

import de.wohlers.strom.administration.Conditions;
import de.wohlers.strom.administration.Place;
import de.wohlers.strom.metering.Reading;
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

    public static Consumption newConsumption(Conditions conditions, Place place, Reading start, Reading end) {
        Consumption out = new Consumption();
        out.placeName = place.getName();
        out.meterName = start.getMeter().getName();
        out.startDate = start.getTimestamp();
        out.endDate = end.getTimestamp();
        out.startReading = start.getReading();
        out.endReading = end.getReading();
        out.difference = end.getReading().subtract(start.getReading());
        out.rate = conditions.getEnergyPrice();
        out.total = out.rate.multiply(out.difference);
        return out;
    }
}
