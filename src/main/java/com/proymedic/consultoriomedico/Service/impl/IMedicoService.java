package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IMedicoService implements MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void saveMedico(Medico medico) {

    }

    @Override
    public void updateMedico(Long id) {

    }

    @Override
    public void deleteMedico(Long id) {

    }

    @Override
    public List<Medico> findAllMedico() {
        return null;
    }
}
