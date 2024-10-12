package ru.alex.NauJava.entities;

import jakarta.persistence.*;
import ru.alex.NauJava.enums.PhoneType;


@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
}