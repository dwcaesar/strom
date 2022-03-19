package de.wohlers.strom.metering;

import de.wohlers.strom.administration.Place;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Meter {

    private int    id;
    private String name;
    private Place  place;
    private Date   installDate;
    private Date   removeDate = null;

}
