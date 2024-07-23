package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Persistence.HorarioDisponibleDAO;
import com.proymedic.consultoriomedico.Repositories.HorarioDisponibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class IHorarioDisponibleDAO implements HorarioDisponibleDAO {
    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

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
        return null; // Crear metodo en el repository para que haga el update correspondiente
    }

    @Override
    public HorarioDisponible findById(Long id) {
        return horarioDisponibleRepository.getById(id);
    }
}
