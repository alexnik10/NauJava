package ru.alex.NauJava.dto.phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import ru.alex.NauJava.enums.PhoneType;

public class PhoneCreateDTO {

    @NotNull(message = "contactId must not be null")
    @Positive(message = "contactId must be a positive number")
    private Long contactId;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\+?[0-9\\-\\s]{7,15}", message = "Invalid phone number format")
    private String number;

    @NotNull(message = "Phone type cannot be null")
    private PhoneType type;

    public @NotBlank(message = "Phone number cannot be blank") @Pattern(regexp = "\\+?[0-9\\-\\s]{7,15}", message = "Invalid phone number format") String getNumber() {
        return number;
    }

    public void setNumber(@NotBlank(message = "Phone number cannot be blank") @Pattern(regexp = "\\+?[0-9\\-\\s]{7,15}", message = "Invalid phone number format") String number) {
        this.number = number;
    }

    public @NotNull(message = "Phone type cannot be null") PhoneType getType() {
        return type;
    }

    public void setType(@NotNull(message = "Phone type cannot be null") PhoneType type) {
        this.type = type;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
