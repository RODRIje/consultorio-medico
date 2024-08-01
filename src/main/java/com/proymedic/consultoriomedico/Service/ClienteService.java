package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;

import java.util.List;

public interface ClienteService {
    // Crear, actualizar, eliminar y listar clientes.

    // Crear Cliente
    void saveCliente(Cliente cliente);

    // Actualizar Cliente
    void updateCliente(Long id, Cliente cliente);
    // Actualizar cliente v2
    Cliente ActuCliente(Cliente cliente);

    // Eliminar Cliente
    void deleteCliente(Long id);

    // Listar todos los cliente
    List<Cliente> findAllCliente();

    // Buscar cliente por id
    Cliente findById(Long id);

    // Crear cliente con return del cliente
    Cliente guardarCliente(Cliente cliente);

}
