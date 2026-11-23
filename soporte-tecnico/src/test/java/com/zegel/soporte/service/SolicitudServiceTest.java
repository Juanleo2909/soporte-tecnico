package com.zegel.soporte.service;

import com.zegel.soporte.dto.ClienteDTO;
import com.zegel.soporte.dto.SolicitudRequestDTO;
import com.zegel.soporte.dto.TecnicoDTO;
import com.zegel.soporte.model.Cliente;
import com.zegel.soporte.model.EstadoSolicitud;
import com.zegel.soporte.model.Solicitud;
import com.zegel.soporte.model.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolicitudServiceTest {
    private ClienteService clienteService;
    private TecnicoService tecnicoService;
    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
        tecnicoService = new TecnicoService();
        solicitudService = new SolicitudService(clienteService, tecnicoService);
    }

    @Test
    void creaSolicitudPendienteYFiltraPorClienteYEstado() {
        Cliente cliente = clienteService.registrar(
                new ClienteDTO("Ana", "ana@example.com", "555-0100", null));

        Solicitud solicitud = solicitudService.crear(
                new SolicitudRequestDTO("No inicia el equipo", cliente.id(), null, null));

        assertEquals(EstadoSolicitud.PENDIENTE, solicitud.estado());
        assertEquals(List.of(solicitud), solicitudService.listarPorCliente(cliente.id()));
        assertEquals(List.of(solicitud), solicitudService.listarTodas(EstadoSolicitud.PENDIENTE));
        assertTrue(solicitudService.listarTodas(EstadoSolicitud.RESUELTA).isEmpty());
    }

    @Test
    void asignaTecnicoExistenteASolicitud() {
        Cliente cliente = clienteService.registrar(
                new ClienteDTO("Ana", "ana@example.com", "555-0100", null));
        Tecnico tecnico = tecnicoService.registrar(
                new TecnicoDTO("Luis", "luis@example.com", "Hardware"));
        Solicitud solicitud = solicitudService.crear(
                new SolicitudRequestDTO("Revisar equipo", cliente.id(), null, null));

        Solicitud asignada = solicitudService.asignarTecnico(solicitud.id(), tecnico.id());

        assertEquals(tecnico, asignada.tecnico());
        assertEquals(EstadoSolicitud.PENDIENTE, asignada.estado());
    }

    @Test
    void rechazaSolicitudDeClienteInexistenteCon404() {
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> solicitudService.crear(
                        new SolicitudRequestDTO("Revisar equipo", 99L, null, null)));

        assertEquals(404, exception.getStatusCode().value());
    }
}
