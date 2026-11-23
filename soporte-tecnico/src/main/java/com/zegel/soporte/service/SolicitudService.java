package com.zegel.soporte.service;

import com.zegel.soporte.dto.SolicitudRequestDTO;
import com.zegel.soporte.model.Cliente;
import com.zegel.soporte.model.EstadoSolicitud;
import com.zegel.soporte.model.Solicitud;
import com.zegel.soporte.model.Tecnico;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SolicitudService implements ISolicitudService {
    private final ConcurrentMap<Long, Solicitud> solicitudes = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();
    private final IClienteService clienteService;
    private final ITecnicoService tecnicoService;

    public SolicitudService(IClienteService clienteService, ITecnicoService tecnicoService) {
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    @Override
    public Solicitud crear(SolicitudRequestDTO dto) {
        Cliente cliente = clienteService.obtenerPorId(dto.clienteId());
        Tecnico tecnico = dto.tecnicoId() == null ? null : tecnicoService.obtenerPorId(dto.tecnicoId());
        EstadoSolicitud estado = dto.estado() == null ? EstadoSolicitud.PENDIENTE : dto.estado();
        long id = ids.incrementAndGet();
        Solicitud solicitud = new Solicitud(id, dto.descripcion(), estado, cliente, tecnico);
        solicitudes.put(id, solicitud);
        return solicitud;
    }

    @Override
    public List<Solicitud> listarTodas(EstadoSolicitud estado) {
        return solicitudes.values().stream()
                .filter(solicitud -> estado == null || solicitud.estado() == estado)
                .sorted(Comparator.comparing(Solicitud::id))
                .toList();
    }

    @Override
    public Solicitud obtenerPorId(Long id) {
        Solicitud solicitud = solicitudes.get(id);
        if (solicitud == null) {
            throw noEncontrada(id);
        }
        return solicitud;
    }

    @Override
    public List<Solicitud> listarPorCliente(Long clienteId) {
        clienteService.obtenerPorId(clienteId);
        return solicitudes.values().stream()
                .filter(solicitud -> solicitud.cliente().id().equals(clienteId))
                .sorted(Comparator.comparing(Solicitud::id))
                .toList();
    }

    @Override
    public Solicitud actualizar(Long id, SolicitudRequestDTO dto) {
        Cliente cliente = clienteService.obtenerPorId(dto.clienteId());
        Tecnico tecnico = dto.tecnicoId() == null ? null : tecnicoService.obtenerPorId(dto.tecnicoId());
        Solicitud solicitud = solicitudes.computeIfPresent(id, (key, actual) -> new Solicitud(
                key,
                dto.descripcion(),
                dto.estado() == null ? actual.estado() : dto.estado(),
                cliente,
                tecnico == null ? actual.tecnico() : tecnico));
        if (solicitud == null) {
            throw noEncontrada(id);
        }
        return solicitud;
    }

    @Override
    public Solicitud asignarTecnico(Long id, Long tecnicoId) {
        Tecnico tecnico = tecnicoService.obtenerPorId(tecnicoId);
        Solicitud solicitud = solicitudes.computeIfPresent(
                id,
                (key, actual) -> new Solicitud(
                        key, actual.descripcion(), actual.estado(), actual.cliente(), tecnico));
        if (solicitud == null) {
            throw noEncontrada(id);
        }
        return solicitud;
    }

    @Override
    public void eliminar(Long id) {
        if (solicitudes.remove(id) == null) {
            throw noEncontrada(id);
        }
    }

    private ResponseStatusException noEncontrada(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada: " + id);
    }
}
