package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IMedicoService implements MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private IMedicoDAO iMedicoDAO;

    @Override
    public void saveMedico(Medico medico) {
        medicoRepository.save(medico);
    }

    @Override
    public void updateMedico(Long id, Medico medico) {
        Medico medicoUpdate = findById(id);
        medicoUpdate.setNombre(medico.getNombre());
        medicoUpdate.setApellido(medico.getApellido());
        medicoUpdate.setMatricula(medico.getMatricula());
        medicoUpdate.setEmail(medico.getEmail());
        medicoUpdate.setEspecialidad(medico.getEspecialidad());
        medicoUpdate.setHorariosDisponibles(medico.getHorariosDisponibles());

        medicoRepository.save(medicoUpdate);
    }

    @Override
    public Boolean deleteMedico(Long id) {
         medicoRepository.deleteById(id);
         return true;
    }

    @Override
    public List<Medico> findAllMedico() {
        return medicoRepository.findAll();
    }
    @Override
    public Medico findById(Long id){
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public Medico guardarMedico(Medico medico) {
        iMedicoDAO.saveMedico(medico);
        return  medico;
    }
}
