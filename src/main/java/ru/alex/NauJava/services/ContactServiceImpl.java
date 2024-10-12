package ru.alex.NauJava.services;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.repositories.ContactRepository;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    private final ContactRepository contactRepository;
    private final EntityManager entityManager;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, EntityManager entityManager) {
        this.contactRepository = contactRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public boolean deleteContactsByIds(List<Long> contactIds) {
        try {
            int deletedCount = contactRepository.deleteContactsByIds(contactIds);
            logger.info("Удалено {} из {} контактов", deletedCount, contactIds.size());
            if (deletedCount != contactIds.size()) {
                throw new IllegalArgumentException("Не все контакты найдены для удаления");
            }
            entityManager.flush();
            entityManager.clear();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
