package ru.alex.NauJava.dto.email;

import ru.alex.NauJava.enums.EmailType;

public class EmailDTO {

    private Long id;
    private String emailAddress;
    private EmailType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }
}
