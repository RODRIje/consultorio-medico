package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;

import java.util.List;

public interface ClienteService {
    // Crear, actualizar, eliminar y listar clientes.

    // Crear Cliente
    void saveCliente(Cliente cliente);

    // Actualizar Cliente
    void updateCliente(Long id);

    // Eliminar Cliente
    void deleteCliente(Long id);

    // Listar todos los cliente
    List<Cliente> findAllCliente();

}
