package ru.alex.NauJava.dto.phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import ru.alex.NauJava.enums.PhoneType;

public class PhoneUpdateDTO {

    @NotNull(message = "Phone ID cannot be null")
    private Long id;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\+?[0-9\\-\\s]{7,15}", message = "Invalid phone number format")
    private String number;

    @NotNull(message = "Phone type cannot be null")
    private PhoneType type;

    public @NotNull(message = "Phone ID cannot be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Phone ID cannot be null") Long id) {
        this.id = id;
    }

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
}
