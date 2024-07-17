package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.MedicoDAO;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IMedicoDAO implements MedicoDAO {
    @Autowired
    private MedicoRepository medicoRepository;
    @Override
    public void saveMedico(Medico medico) {
        medicoRepository.save(medico);
    }

    @Override
    public void updateMedico(Long id) {

    }

    @Override
    public void deleteMedico(Long id) {
        medicoRepository.deleteById(id);
    }

    @Override
    public List<Medico> findAllMedico() {
        return medicoRepository.findAll();
    }
}
