package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.HorarioDisponibleRepository;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class IHorarioDisponibleService implements HorarioDisponibleService {
    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void saveHorarioDispo(HorarioDisponible horarioDisponible) {
        horarioDisponibleRepository.save(horarioDisponible);
    }

    @Override
    public List<HorarioDisponible> findAllHorarios() {
        return horarioDisponibleRepository.findAll();
    }

    @Override
    public void deleteHorarioDispo(Long id) {
        horarioDisponibleRepository.deleteById(id);
    }

    @Override
    public void updateHorario(Long id, HorarioDisponible horarioDisponible) {
        HorarioDisponible horarioUpdate = findById(id);
        Medico medico = medicoRepository.findById(horarioDisponible.getMedico().getId()).get();

        horarioUpdate.setHoraFin(horarioDisponible.getHoraFin());
        horarioUpdate.setHoraInicio(horarioDisponible.getHoraInicio());
        horarioUpdate.setDiaSemana(horarioDisponible.getDiaSemana());
        horarioUpdate.setMedico(medico);

        horarioDisponibleRepository.save(horarioUpdate);
    }

    @Override
    public HorarioDisponible actHorario(HorarioDisponible horarioDisponible) {
        return horarioDisponibleRepository.save(horarioDisponible);
    }

    @Override
    public HorarioDisponible findById(Long id) {
        return horarioDisponibleRepository.findById(id).orElse(null);
    }

    @Override
    public HorarioDisponible guardarHorario(HorarioDisponible horarioDisponible) {
        horarioDisponibleRepository.save(horarioDisponible);
        return horarioDisponible;
    }
}
