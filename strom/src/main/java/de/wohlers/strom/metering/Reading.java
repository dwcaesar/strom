package de.wohlers.strom.metering;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Reading {

    private int        id;
    private Date       timestamp;
    private Meter      meter;
    private BigDecimal reading;

}
