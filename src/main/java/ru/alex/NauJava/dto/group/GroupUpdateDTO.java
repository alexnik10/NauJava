package ru.alex.NauJava.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroupUpdateDTO {

    @NotNull(message = "Group ID cannot be null")
    private Long id;

    @NotBlank(message = "Group name cannot be blank")
    private String name;

    private String description;

    public @NotNull(message = "Group ID cannot be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Group ID cannot be null") Long id) {
        this.id = id;
    }

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
