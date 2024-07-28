package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.IHorarioDisponibleDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
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
    public HorarioDisponible updateHorario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        return null;
    }

    @Override
    public HorarioDisponible findById(Long id) {
        return horarioDisponibleRepository.findById(id).orElse(null);
    }
}
