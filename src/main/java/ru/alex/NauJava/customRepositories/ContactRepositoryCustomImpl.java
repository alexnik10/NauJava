package ru.alex.NauJava.customRepositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Group;

import java.util.List;

@Repository
public class ContactRepositoryCustomImpl implements ContactRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public ContactRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Contact> findByFirstNameOrLastName(String firstName, String lastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = cb.createQuery(Contact.class);
        Root<Contact> contactRoot = query.from(Contact.class);
        Predicate firstNamePredicate = cb.equal(contactRoot.get("firstName"), firstName);
        Predicate lastNamePredicate = cb.equal(contactRoot.get("lastName"), lastName);
        Predicate finalPredicate = cb.or(firstNamePredicate, lastNamePredicate);
        query.select(contactRoot).where(finalPredicate);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Contact> findContactsByGroupName(String groupName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = cb.createQuery(Contact.class);
        Root<Contact> contactRoot = query.from(Contact.class);
        Join<Contact, Group> groupJoin = contactRoot.join("groups", JoinType.INNER);
        Predicate groupNamePredicate = cb.equal(groupJoin.get("name"), groupName);
        query.select(contactRoot).where(groupNamePredicate).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
