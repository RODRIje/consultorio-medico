package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class IMedicoService implements MedicoService {
    @Autowired
    private IMedicoDAO iMedicoDAO;

    @Override
    public void saveMedico(Medico medico) {
        iMedicoDAO.saveMedico(medico);
    }

    @Override
    public Medico updateMedico(Long id, Medico medico) {
        Medico medicoUpdate = iMedicoDAO.updateMedico(id, medico);
        return  medicoUpdate;
    }

    @Override
    public void deleteMedico(Long id) {
        iMedicoDAO.deleteMedico(id);
    }

    @Override
    public List<Medico> findAllMedico() {
        return iMedicoDAO.findAllMedico();
    }
}
