package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;

import java.util.List;

public interface ClienteService {
    // Crear, actualizar, eliminar y listar clientes.

    // Crear Cliente
    void saveCliente(Cliente cliente);

    // Actualizar Cliente
    Cliente updateCliente(Long id, Cliente cliente);

    // Eliminar Cliente
    void deleteCliente(Long id);

    // Listar todos los cliente
    List<Cliente> findAllCliente();

    // Buscar cliente por id
    Cliente findById(Long id);

}
