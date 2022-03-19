package de.wohlers.strom.contracts;

import de.wohlers.strom.administration.Place;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Contract {

    private int    id;
    private Place  place;
    private Member member;
    private Date   validFrom;
    private Date   validUntil = null;

    /**
     * @return Contracts of that member that are active in the period between startDate and endDate
     */
    public static List<Contract> findActiveContractsForMemberAndTimePeriod(Member member, Date startDate, Date endDate) {
        return List.of(); // TODO
    }

    /**
     * @return true, when the very first contract for that member is in the given period between startDate and endDate
     */
    public static boolean firstContractForMemberLiesInTimePeriod(Member member, Date startDate, Date endDate) {
        return false; // TODO
    }
}
