package ru.alex.NauJava.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddressUpdateDTO {

    @NotNull(message = "Address ID cannot be null")
    private Long id;

    private String street;

    private String city;

    @Pattern(regexp = "^$|^[0-9A-Za-z\\s-]{3,10}$",
            message = "Postal code must be empty or between 3 and 10 characters, containing letters, digits, spaces, or hyphens")
    private String postalCode;

    private String country;

    public @NotNull(message = "Address ID cannot be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Address ID cannot be null") Long id) {
        this.id = id;
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
