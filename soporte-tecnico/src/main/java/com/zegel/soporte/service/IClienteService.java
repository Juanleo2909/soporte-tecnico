package com.zegel.soporte.service;

import com.zegel.soporte.dto.ClienteDTO;
import com.zegel.soporte.model.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente registrar(ClienteDTO dto);

    List<Cliente> listarTodos();

    Cliente obtenerPorId(Long id);

    Cliente actualizar(Long id, ClienteDTO dto);

    void eliminar(Long id);
}
