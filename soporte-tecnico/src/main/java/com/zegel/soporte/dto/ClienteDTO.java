package com.zegel.soporte.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank String nombre,
        @JsonAlias("correo") @NotBlank @Email String email,
        @NotBlank String telefono,
        String empresa) {
}
