package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.CitaDAO;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ICitaDAO implements CitaDAO {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public void saveCita(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public void updateCita(Long id, Cita cita) {
        Cita citaUpdate = findById(id);
        Medico medico =  medicoRepository.getById(cita.getId());
        Cliente cliente = clienteRepository.getById(cita.getId());

        citaUpdate.setObservaciones(cita.getObservaciones());
        citaUpdate.setHora(cita.getHora());
        citaUpdate.setFecha(cita.getFecha());
        citaUpdate.setMedico(medico);
        citaUpdate.setCliente(cliente);

        citaRepository.save(citaUpdate);
    }

    @Override
    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> findAllCita() {
        return citaRepository.findAll();
    }

    @Override
    public Cita findById(Long id) {
         return citaRepository.getById(id);
    }
}
