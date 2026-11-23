package com.zegel.soporte.service;

import com.zegel.soporte.dto.ClienteDTO;
import com.zegel.soporte.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClienteService implements IClienteService {
    private final ConcurrentMap<Long, Cliente> clientes = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    @Override
    public Cliente registrar(ClienteDTO dto) {
        long id = ids.incrementAndGet();
        Cliente cliente = new Cliente(id, dto.nombre(), dto.email(), dto.telefono(), dto.empresa());
        clientes.put(id, cliente);
        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() {
        return clientes.values().stream()
                .sorted(Comparator.comparing(Cliente::id))
                .toList();
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            throw noEncontrado(id);
        }
        return cliente;
    }

    @Override
    public Cliente actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clientes.computeIfPresent(
                id,
                (key, actual) -> new Cliente(
                        key, dto.nombre(), dto.email(), dto.telefono(), dto.empresa()));
        if (cliente == null) {
            throw noEncontrado(id);
        }
        return cliente;
    }

    @Override
    public void eliminar(Long id) {
        if (clientes.remove(id) == null) {
            throw noEncontrado(id);
        }
    }

    private ResponseStatusException noEncontrado(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado: " + id);
    }
}
