package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Persistence.HorarioDisponibleDAO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class IHorarioDisponibleDAO implements HorarioDisponibleDAO {
    @Override
    public void saveHorarioDispo(HorarioDisponible horarioDisponible) {

    }

    @Override
    public List<HorarioDisponible> findAllHorarios() {
        return null;
    }

    @Override
    public void deleteHorarioDispo(Long id) {

    }

    @Override
    public HorarioDisponible updateHorario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        return null;
    }
}
