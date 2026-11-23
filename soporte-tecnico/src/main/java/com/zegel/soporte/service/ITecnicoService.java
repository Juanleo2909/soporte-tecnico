package com.zegel.soporte.service;

import com.zegel.soporte.dto.TecnicoDTO;
import com.zegel.soporte.model.Tecnico;

import java.util.List;

public interface ITecnicoService {
    Tecnico registrar(TecnicoDTO dto);

    List<Tecnico> listarTodos();

    Tecnico obtenerPorId(Long id);

    Tecnico actualizar(Long id, TecnicoDTO dto);

    void eliminar(Long id);
}
