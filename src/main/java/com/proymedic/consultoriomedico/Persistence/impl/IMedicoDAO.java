package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.MedicoDAO;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IMedicoDAO implements MedicoDAO {
    @Autowired
    private MedicoRepository medicoRepository;
    @Override
    public void saveMedico(Medico medico) {
        medicoRepository.save(medico);
    }

    @Override
    public Medico updateMedico(Long id, Medico medico) {
        Medico medicoUpdate = medicoRepository.findById(id).get();

        medicoUpdate.setNombre(medico.getNombre());
        medicoUpdate.setApellido(medico.getApellido());
        medicoUpdate.setEmail(medico.getEmail());
        medicoUpdate.setEspecialidad(medico.getEspecialidad());
        medicoUpdate.setHorariosDisponibles(medico.getHorariosDisponibles());
        medicoUpdate.setMatricula(medico.getMatricula());

        return medicoUpdate;
    }

    @Override
    public Boolean deleteMedico(Long id) {
        try {
            medicoRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Medico> findAllMedico() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico findById(Long id){
        return medicoRepository.getById(id);
    }
}
