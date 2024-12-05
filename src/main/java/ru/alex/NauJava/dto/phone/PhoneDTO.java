package ru.alex.NauJava.dto.phone;

import ru.alex.NauJava.enums.PhoneType;

public class PhoneDTO {
    private Long id;
    private String number;
    private PhoneType type;

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

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
}
