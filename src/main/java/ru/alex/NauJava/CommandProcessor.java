package ru.alex.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.services.ContactService;

@Component
public class CommandProcessor
{
    private final ContactService contactService;

    @Autowired
    public CommandProcessor(ContactService contactService)
    {
        this.contactService = contactService;
    }

    public void processCommand(String input)
    {
        String[] cmd = input.split(" ");
        switch (cmd[0])
        {
            case "create" ->
            {
                contactService.createContact(cmd[1], cmd[2]);
                System.out.println("Контакт успешно добавлен...");
            }
            case "delete" ->
            {
                contactService.deleteById(Long.parseLong(cmd[1]));
                System.out.println("Контакт успешно удалён...");
            }
            case "find" ->
            {
                Contact contact = contactService.findById(Long.parseLong(cmd[1]));
                System.out.println("{\n    id: " + contact.getId() + ",\n    number: " + contact.getPhoneNumber() + ",\n    name: " + contact.getName() + "\n}");
            }
            case "updateNumber" ->
            {
                contactService.updateNumber(Long.parseLong(cmd[1]), cmd[2]);
                System.out.println("Обновлён номер контакта.");
            }
            case "updateName" ->
            {
                contactService.updateName(Long.parseLong(cmd[1]), cmd[2]);
                System.out.println("Обновлено имя контакта.");
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}