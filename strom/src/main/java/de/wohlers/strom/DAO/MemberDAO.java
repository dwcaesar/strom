package de.wohlers.strom.DAO;

import de.wohlers.strom.Models.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberDAO extends DAO<Member> {

    private static MemberDAO instance;

    public static MemberDAO getInstance() {
        if(instance == null) {
            instance = new MemberDAO();
        }
        return instance;
    }

    public List<Member> findAll() {
        EntityManager em = getManager();
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

}
