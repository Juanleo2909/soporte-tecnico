package com.zegel.soporte.model;

public record Solicitud(
        Long id,
        String descripcion,
        EstadoSolicitud estado,
        Cliente cliente,
        Tecnico tecnico) {
}
