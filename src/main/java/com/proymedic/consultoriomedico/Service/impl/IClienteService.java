package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IClienteService implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void saveCliente(Cliente cliente) {

    }

    @Override
    public void updateCliente(Long id) {

    }

    @Override
    public void deleteCliente(Long id) {

    }

    @Override
    public List<Cliente> findAllCliente() {
        return null;
    }
}
