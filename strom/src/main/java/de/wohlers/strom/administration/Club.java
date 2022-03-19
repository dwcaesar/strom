package de.wohlers.strom.administration;

import de.wohlers.strom.contracts.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    /**
     * @return current Club information
     */
    public static Club get() {
        return null; // TODO
    }

}
