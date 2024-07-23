package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.ICitaDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IClienteDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
import com.proymedic.consultoriomedico.Service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ICitaService implements CitaService {
    @Autowired
    private ICitaDAO iCitaDAO;
    @Autowired
    private IClienteDAO iClienteDAO;
    @Autowired
    private IMedicoDAO iMedicoDAO;

    @Override
    public void saveCita(Cita cita) {
        iCitaDAO.saveCita(cita);
    }

    @Override
    public void updateCita(Long id, Cita cita) {
        Cita citaUpdate = findById(id);
        Medico medico = iMedicoDAO.findById(cita.getId());
        Cliente cliente = iClienteDAO.findById(cita.getId());

        citaUpdate.setCliente(cliente);
        citaUpdate.setMedico(medico);
        citaUpdate.setHora(cita.getHora());
        citaUpdate.setFecha(cita.getFecha());
        citaUpdate.setObservaciones(cita.getObservaciones());

        iCitaDAO.saveCita(citaUpdate);
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
    public Cita findById(Long id) {
        return iCitaDAO.findById(id);
    }
}
