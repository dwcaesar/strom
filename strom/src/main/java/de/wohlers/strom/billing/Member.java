package de.wohlers.strom.billing;

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

    public static Member fromMember(de.wohlers.strom.contracts.Member member) {
        Member out = new Member();
        out.name = member.getName();
        out.street = member.getStreet();
        out.streetNumber = member.getStreetNumber();
        out.postalCode = member.getPostalCode();
        out.city = member.getCity();
        out.mailAddress = member.getMailAddress();
        out.method = DeliveryMethod.fromMethod(member.getMethod());
        return out;
    }

    public enum DeliveryMethod {
        E_MAIL, PRINT_OUT;

        public static DeliveryMethod fromMethod(de.wohlers.strom.contracts.Member.DeliveryMethod method) {
            for (DeliveryMethod value : values()) {
                if (value.name().equals(method.name())) {
                    return value;
                }
            }
            return null;
        }
    }

}
