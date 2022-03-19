package de.wohlers.strom.contracts;

import de.wohlers.strom.administration.Place;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Contract {

    private int    id;
    private Place  place;
    private Member member;
    private Date   validFrom;
    private Date   validUntil = null;

}
