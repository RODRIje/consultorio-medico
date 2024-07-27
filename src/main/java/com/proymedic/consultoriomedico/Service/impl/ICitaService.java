package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.ICitaDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IClienteDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ICitaService implements CitaService {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void saveCita(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public void updateCita(Long id, Cita cita) {
        Cita citaUpdate = findById(id);
        Medico medico = medicoRepository.findById(cita.getId()).get();
        Cliente cliente = clienteRepository.findById(cita.getId()).get();

        citaUpdate.setCliente(cliente);
        citaUpdate.setMedico(medico);
        citaUpdate.setHora(cita.getHora());
        citaUpdate.setFecha(cita.getFecha());
        citaUpdate.setObservaciones(cita.getObservaciones());

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
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public Cita guardarCita(Cita cita) {
        citaRepository.save(cita);
        return cita;
    }


}
