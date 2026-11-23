package com.zegel.soporte.service;

import com.zegel.soporte.dto.TecnicoDTO;
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
public class TecnicoService implements ITecnicoService {
    private final ConcurrentMap<Long, Tecnico> tecnicos = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    @Override
    public Tecnico registrar(TecnicoDTO dto) {
        long id = ids.incrementAndGet();
        Tecnico tecnico = new Tecnico(id, dto.nombre(), dto.email(), dto.especialidad());
        tecnicos.put(id, tecnico);
        return tecnico;
    }

    @Override
    public List<Tecnico> listarTodos() {
        return tecnicos.values().stream()
                .sorted(Comparator.comparing(Tecnico::id))
                .toList();
    }

    @Override
    public Tecnico obtenerPorId(Long id) {
        Tecnico tecnico = tecnicos.get(id);
        if (tecnico == null) {
            throw noEncontrado(id);
        }
        return tecnico;
    }

    @Override
    public Tecnico actualizar(Long id, TecnicoDTO dto) {
        Tecnico tecnico = tecnicos.computeIfPresent(
                id,
                (key, actual) -> new Tecnico(key, dto.nombre(), dto.email(), dto.especialidad()));
        if (tecnico == null) {
            throw noEncontrado(id);
        }
        return tecnico;
    }

    @Override
    public void eliminar(Long id) {
        if (tecnicos.remove(id) == null) {
            throw noEncontrado(id);
        }
    }

    private ResponseStatusException noEncontrado(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado: " + id);
    }
}
