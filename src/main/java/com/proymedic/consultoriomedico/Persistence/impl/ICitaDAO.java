package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Persistence.CitaDAO;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ICitaDAO implements CitaDAO {
    @Autowired
    private CitaRepository citaRepository;
    @Override
    public void saveCita(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public Cita updateCita(Long id) {
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> findAllCita() {
        return citaRepository.findAll();
    }
}
