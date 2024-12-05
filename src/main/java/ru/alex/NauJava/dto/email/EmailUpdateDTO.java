package ru.alex.NauJava.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ru.alex.NauJava.enums.EmailType;

public class EmailUpdateDTO {

    @NotNull(message = "Email ID cannot be null")
    private Long id;

    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Invalid email address format")
    private String emailAddress;

    @NotNull(message = "Email type cannot be null")
    private EmailType type;

    public @NotNull(message = "Email ID cannot be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Email ID cannot be null") Long id) {
        this.id = id;
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
