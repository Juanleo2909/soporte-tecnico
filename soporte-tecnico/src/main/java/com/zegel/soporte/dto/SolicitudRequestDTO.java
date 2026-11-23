package com.zegel.soporte.dto;

import com.zegel.soporte.model.EstadoSolicitud;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitudRequestDTO(
        @NotBlank String descripcion,
        @NotNull Long clienteId,
        Long tecnicoId,
        EstadoSolicitud estado) {
}
