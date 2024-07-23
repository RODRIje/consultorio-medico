package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Persistence.impl.ICitaDAO;
import com.proymedic.consultoriomedico.Service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ICitaService implements CitaService {
    @Autowired
    private ICitaDAO iCitaDAO;

    @Override
    public void saveCita(Cita cita) {
        iCitaDAO.saveCita(cita);
    }

    @Override
    public void updateCita(Long id) {

    }

    @Override
    public void deleteCita(Long id) {
        iCitaDAO.deleteCita(id);
    }

    @Override
    public List<Cita> findAllCita() {
        return iCitaDAO.findAllCita();
    }

    @Override
    public Optional<Cita> findById(Long id) {
        return iCitaDAO.findById(id);
    }
}
