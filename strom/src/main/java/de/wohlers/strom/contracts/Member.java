package de.wohlers.strom.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private int            id;
    private String         name;
    private String         street;
    private String         streetNumber;
    private int            postalCode;
    private String         city;
    private String         mailAddress = null;
    private DeliveryMethod method      = DeliveryMethod.PRINT_OUT;

    public enum DeliveryMethod {
        E_MAIL, PRINT_OUT
    }
}
