package ru.alex.NauJava.exceptions;

public class ContactNotFoundException extends NotFoundException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
