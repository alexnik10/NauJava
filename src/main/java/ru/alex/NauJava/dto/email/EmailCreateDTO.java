package ru.alex.NauJava.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.alex.NauJava.enums.EmailType;

public class EmailCreateDTO {

    @NotNull(message = "contactId must not be null")
    @Positive(message = "contactId must be a positive number")
    private Long contactId;

    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Invalid email address format")
    private String emailAddress;

    @NotNull(message = "Email type cannot be null")
    private EmailType type;

    public @NotNull(message = "contactId must not be null") @Positive(message = "contactId must be a positive number") Long getContactId() {
        return contactId;
    }

    public void setContactId(@NotNull(message = "contactId must not be null") @Positive(message = "contactId must be a positive number") Long contactId) {
        this.contactId = contactId;
    }

    public @NotBlank(message = "Email address cannot be blank") @Email(message = "Invalid email address format") String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@NotBlank(message = "Email address cannot be blank") @Email(message = "Invalid email address format") String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public @NotNull(message = "Email type cannot be null") EmailType getType() {
        return type;
    }

    public void setType(@NotNull(message = "Email type cannot be null") EmailType type) {
        this.type = type;
    }
}
