package com.zegel.soporte.service;

import com.zegel.soporte.dto.SolicitudRequestDTO;
import com.zegel.soporte.model.EstadoSolicitud;
import com.zegel.soporte.model.Solicitud;

import java.util.List;

public interface ISolicitudService {
    Solicitud crear(SolicitudRequestDTO dto);

    List<Solicitud> listarTodas(EstadoSolicitud estado);

    Solicitud obtenerPorId(Long id);

    List<Solicitud> listarPorCliente(Long clienteId);

    Solicitud actualizar(Long id, SolicitudRequestDTO dto);

    Solicitud asignarTecnico(Long id, Long tecnicoId);

    void eliminar(Long id);
}
