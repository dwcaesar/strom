package de.wohlers.strom.billing;

import de.wohlers.strom.contracts.Member;
import lombok.Getter;

@Getter
public class Club {

    private int    id;
    private String name;
    private String street;
    private String streetNumber;
    private int    postalCode;
    private String city;
    /**
     * Member who issues electricity bills
     */
    private Member responsible;
    /**
     * Member who is responsible for finances
     */
    private Member finance;

}
