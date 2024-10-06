package ru.alex.NauJava.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.entities.Contact;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactRepository implements CrudRepository<Contact, Long> {
    private final List<Contact> contactContainer;
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Autowired
    public ContactRepository(List<Contact> contactContainer) {
        this.contactContainer = contactContainer;
    }

    @Override
    public void create(Contact contact) {
        contact.setId(idGenerator.incrementAndGet());
        contactContainer.add(contact);
    }

    @Override
    public Contact read(Long id) {
        return contactContainer.stream()
                .filter(contact -> id.equals(contact.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Contact contact) {
        for (int i = 0; i < contactContainer.size(); i++) {
            if (contact.getId() == contactContainer.get(i).getId()) {
                contactContainer.set(i, contact);
                return;
            }
        }
        throw new RuntimeException("Контакт с ID " + contact.getId() + " не найден.");
    }

    @Override
    public void delete(Long id) {
        boolean removed = contactContainer.removeIf(contact -> id.equals(contact.getId()));
        if (!removed) {
            throw new RuntimeException("Контакт с ID " + id + " не найден для удаления.");
        }
    }
}
