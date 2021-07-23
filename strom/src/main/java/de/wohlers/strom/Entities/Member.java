package de.wohlers.strom.Entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import java.util.List;

@javax.persistence.Entity
@Access (AccessType.FIELD)
public class Member extends Entity {
    @Id
    private int                id;
    private String             title;
    private String             name;
    private String             street;
    private String             zip;
    private String             city;
    private String             phone;
    private String             email;
    private String             ePost;
    private boolean            directDebit;
    private NotificationMethod preferredNotificationMethod;

    public static List<Member> findAll() {
        return getManager().createQuery("SELECT p FROM Member p", Member.class).getResultList();
    }

}
