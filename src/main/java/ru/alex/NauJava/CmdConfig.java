package ru.alex.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class CmdConfig {
    @Autowired
    private CommandProcessor commandProcessor;

    @Bean
    public CommandLineRunner commandScanner()
    {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in))
            {
                System.out.println("Доступные команды для работы с контактами::");
                System.out.println("create <номер телефона> <имя>");
                System.out.println("delete <id>");
                System.out.println("find <id>");
                System.out.println("updateNumber <id> <новый номер телефона>");
                System.out.println("updateName <id> <новое имя>");
                System.out.println("exit\n");
                while (true)
                {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim()))
                    {
                        System.out.println("Выход из программы...");
                        break;
                    }
                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}
