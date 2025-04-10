package io.github.mariacsr.quarkussocial.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotBlank(message = "Name is Required") String name, @NotNull(message = "Age is Required") Integer age) {
}
