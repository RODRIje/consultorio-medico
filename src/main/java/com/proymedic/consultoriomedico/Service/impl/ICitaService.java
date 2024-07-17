package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ICitaService implements CitaService {
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
