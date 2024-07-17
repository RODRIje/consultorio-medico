package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Persistence.impl.IClienteDAO;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IClienteService implements ClienteService {
    @Autowired
    private IClienteDAO iClienteDAO;

    @Override
    public void saveCliente(Cliente cliente) {
        iClienteDAO.saveCliente(cliente);
    }

    @Override
    public void updateCliente(Long id) {

    }

    @Override
    public void deleteCliente(Long id) {
        iClienteDAO.deleteCliente(id);
    }

    @Override
    public List<Cliente> findAllCliente() {
        return iClienteDAO.findAllCliente();
    }
}
