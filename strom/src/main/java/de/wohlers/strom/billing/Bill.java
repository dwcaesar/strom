package de.wohlers.strom.billing;

import de.wohlers.strom.administration.Conditions;
import de.wohlers.strom.contracts.Contract;
import de.wohlers.strom.metering.Reading;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class Bill {

    private final List<Consumption> consumptions = new ArrayList<>();
    private final List<Fee>         fees         = new ArrayList<>();
    private       long              id;
    private       Date              creationDate;
    private       TimePeriod        timePeriod;
    private       Member            recipient;
    private       Club              club;
    private       BigDecimal        total        = new BigDecimal(0);

    public static Bill buildBillForMemberAndTimePeriod(de.wohlers.strom.contracts.Member member, Date startDate, Date endDate) {
        Bill out = new Bill();
        out.creationDate = new Date();
        out.timePeriod = new TimePeriod(startDate, endDate);
        out.recipient = Member.fromMember(member);
        out.club = Club.fromClub(de.wohlers.strom.administration.Club.get());

        boolean applySetupFee = Contract.firstContractForMemberLiesInTimePeriod(member, startDate, endDate);
        if (applySetupFee) {
            Conditions currentConditions = Conditions.findCurrentConditions(out.creationDate);
            out.fees.add(Fee.newSetupFee(currentConditions));
        }

        List<Contract>   contracts  = Contract.findActiveContractsForMemberAndTimePeriod(member, startDate, endDate);
        List<Conditions> conditions = Conditions.findConditionsInTimePeriod(startDate, endDate);

        for (Contract contract : contracts) {
            for (int i = 0; i < conditions.size(); i++) {
                Conditions currentConditions = conditions.get(0);
                Conditions nextConditions    = i + 1 < conditions.size() ? conditions.get(i + 1) : null;

                if (currentConditions.getValidFrom().after(contract.getValidUntil())) {
                    // contract was not valid when currentConditions started
                    continue;
                }

                if (nextConditions != null && nextConditions.getValidFrom().before(contract.getValidFrom())) {
                    // currentConditions were not valid when contract started
                    continue;
                }

                Date periodStartDate;
                if (startDate.after(contract.getValidFrom()) && startDate.after(currentConditions.getValidFrom())) {
                    periodStartDate = startDate;
                } else if (contract.getValidFrom().after(startDate) && contract.getValidFrom().after(currentConditions.getValidFrom())) {
                    periodStartDate = contract.getValidFrom();
                } else {
                    periodStartDate = currentConditions.getValidFrom();
                }

                Date periodEndDate;
                if (endDate.before(contract.getValidUntil()) && (nextConditions == null || endDate.before(nextConditions.getValidFrom()))) {
                    periodEndDate = endDate;
                } else if (contract.getValidUntil().before(endDate) && (nextConditions == null || contract.getValidUntil().before(nextConditions.getValidFrom()))) {
                    periodEndDate = contract.getValidUntil();
                } else if (nextConditions != null) {
                    periodEndDate = nextConditions.getValidFrom();
                } else {
                    periodEndDate = endDate;
                }

                out.fees.add(Fee.newBasic(contract.getPlace().getName(), currentConditions, periodStartDate, periodEndDate));

                List<Reading> readings = Reading.findReadingsForPlaceInPeriod(contract.getPlace(), periodStartDate, periodEndDate);

                for (int j = 0; j < readings.size() - 1; j++) {
                    Reading start = readings.get(j);
                    Reading end   = readings.get(j + 1);

                    out.consumptions.add(Consumption.newConsumption(currentConditions, contract.getPlace(), start, end));
                }
            }
        }

        for (Fee fee : out.fees) {
            out.total = out.total.add(fee.getTotal());
        }
        for (Consumption consumption : out.consumptions) {
            out.total = out.total.add(consumption.getTotal());
        }

        return out;
    }
}
