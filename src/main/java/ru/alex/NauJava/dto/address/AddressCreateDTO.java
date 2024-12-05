package ru.alex.NauJava.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddressCreateDTO {

    @NotNull(message = "Contact ID must not be null")
    private Long contactId;

    private String street;

    private String city;

    @Pattern(regexp = "^$|^[0-9A-Za-z\\s-]{3,10}$",
            message = "Postal code must be empty or between 3 and 10 characters, containing letters, digits, spaces, or hyphens")
    private String postalCode;

    private String country;

    public @NotNull(message = "Contact ID must not be null") Long getContactId() {
        return contactId;
    }

    public void setContactId(@NotNull(message = "Contact ID must not be null") Long contactId) {
        this.contactId = contactId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public @Pattern(regexp = "^$|^[0-9A-Za-z\\s-]{3,10}$",
            message = "Postal code must be empty or between 3 and 10 characters, containing letters, digits, spaces, or hyphens") String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@Pattern(regexp = "^$|^[0-9A-Za-z\\s-]{3,10}$",
            message = "Postal code must be empty or between 3 and 10 characters, containing letters, digits, spaces, or hyphens") String postalCode) {
        this.postalCode = postalCode;
    }
}
