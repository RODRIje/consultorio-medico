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
    private IMedicoDAO iMedicoDAO;

    @Override
    public void saveMedico(Medico medico) {
        iMedicoDAO.saveMedico(medico);
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

        iMedicoDAO.saveMedico(medicoUpdate);
    }

    @Override
    public Boolean deleteMedico(Long id) {
        return iMedicoDAO.deleteMedico(id);
    }

    @Override
    public List<Medico> findAllMedico() {
        return iMedicoDAO.findAllMedico();
    }
    @Override
    public Medico findById(Long id){
        return iMedicoDAO.findById(id);
    }
}
