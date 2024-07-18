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
    public void updateCliente(Long id) {

    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> findAllCliente() {
        return clienteRepository.findAll();
    }
}
