package ru.alex.NauJava.dto.group;

import jakarta.validation.constraints.NotBlank;

public class GroupCreateDTO {

    @NotBlank(message = "Group name cannot be blank")
    private String name;

    private String description;

    public @NotBlank(message = "Group name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Group name cannot be blank") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
