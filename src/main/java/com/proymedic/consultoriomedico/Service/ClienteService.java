package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Crear metodo para traer todos los clientes que tengan obra social
    List<Cliente> findClienteTrueObraSocial(Boolean obraSocial);

    // Crear metodo para traer todos los clientes que tengan X obra social
    List<Cliente> findClienteMismaObraSocial(String nombreObraSocial);

    // Crear metodo para traer clientes por su nombre
    List<Cliente> findClienteByName(String nombre);

}
