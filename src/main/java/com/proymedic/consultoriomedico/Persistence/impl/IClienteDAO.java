package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Persistence.ClienteDAO;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IClienteDAO implements ClienteDAO {
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Long id, Cliente cliente) {
        Cliente clienteUpdate = clienteRepository.findById(id).get();

        clienteUpdate.setNombre(cliente.getNombre());
        clienteUpdate.setApellido(cliente.getApellido());
        clienteUpdate.setEmail(cliente.getEmail());
        clienteUpdate.setObraSocial(cliente.getObraSocial());
        clienteUpdate.setNombreObraSocial(cliente.getNombreObraSocial());

        return clienteUpdate;
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> findAllCliente() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.getById(id);
    }
}
