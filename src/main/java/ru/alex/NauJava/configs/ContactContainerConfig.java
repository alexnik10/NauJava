package ru.alex.NauJava.configs;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.alex.NauJava.entities.Contact;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ContactContainerConfig
{
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Contact> contactContainer()
    {
        return new ArrayList<>();
    }

}