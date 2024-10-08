package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IClienteService implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }
    @Override
    public void updateCliente(Long id, Cliente cliente) {
        Cliente clienteUpdate = findById(id);
        clienteUpdate.setNombre(cliente.getNombre());
        clienteUpdate.setApellido(cliente.getApellido());
        clienteUpdate.setEmail(cliente.getEmail());
        clienteUpdate.setObraSocial(cliente.getObraSocial());
        clienteUpdate.setNombreObraSocial(cliente.getNombreObraSocial());

        clienteRepository.save(clienteUpdate);
    }
    @Override
    public Cliente ActuCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
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
        return clienteRepository.findById(id).orElse(null);
    }
    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    @Override
    public List<Cliente> findClienteTrueObraSocial(Boolean obraSocial) {
        return clienteRepository.findClienteTrueObraSocial(obraSocial);
    }
    @Override
    public List<Cliente> findClienteMismaObraSocial(String nombreObraSocial) {
        return clienteRepository.findClienteMismaObraSocial(nombreObraSocial);
    }
    @Override
    public List<Cliente> findClienteByName(String nombre) {
        return clienteRepository.findClienteByName(nombre);
    }
}
