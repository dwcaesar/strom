package de.wohlers.strom.billing;

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

    public static Club fromClub(de.wohlers.strom.administration.Club club) {
        Club out = new Club();
        out.name = club.getName();
        out.street = club.getStreet();
        out.streetNumber = club.getStreetNumber();
        out.postalCode = club.getPostalCode();
        out.city = club.getCity();
        out.responsible = Member.fromMember(club.getResponsible());
        out.finance = Member.fromMember(club.getFinance());
        return out;
    }
}
