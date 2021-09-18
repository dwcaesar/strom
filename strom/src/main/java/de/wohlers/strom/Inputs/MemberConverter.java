package de.wohlers.strom.Inputs;

import de.wohlers.strom.Models.Member;
import javafx.util.StringConverter;

public class MemberConverter extends RequiredStringConverter<Member> {
    private static StringConverter<Member> instance;

    public static StringConverter<Member> getInstance() {
        if (instance == null) {
            instance = new MemberConverter();
        }
        return instance;
    }

    @Override
    public String nonNullToString(Member object) {
        return object.getName();
    }

    @Override
    public Member fromString(String string) {
        // Umgekehrter Weg ist nicht vorgesehen
        return null;
    }
}
