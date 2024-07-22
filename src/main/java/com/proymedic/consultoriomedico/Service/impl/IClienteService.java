package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.IClienteDAO;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IClienteService implements ClienteService {
    @Autowired
    private IClienteDAO iClienteDAO;

    @Override
    public void saveCliente(Cliente cliente) {
        iClienteDAO.saveCliente(cliente);
    }
    @Override
    public void updateCliente(Long id, Cliente cliente) {
        Cliente clienteUpdate = findById(id);
        clienteUpdate.setNombre(cliente.getNombre());
        clienteUpdate.setApellido(cliente.getApellido());
        clienteUpdate.setEmail(cliente.getEmail());
        clienteUpdate.setObraSocial(cliente.getObraSocial());
        clienteUpdate.setNombreObraSocial(cliente.getNombreObraSocial());

        iClienteDAO.saveCliente(clienteUpdate);
    }
    @Override
    public void deleteCliente(Long id) {
        iClienteDAO.deleteCliente(id);
    }
    @Override
    public List<Cliente> findAllCliente() {
        return iClienteDAO.findAllCliente();
    }
    @Override
    public Cliente findById(Long id) {
        return iClienteDAO.findById(id);
    }
}
