package com.proymedic.consultoriomedico.Persistence.impl;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Persistence.HorarioDisponibleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class IHorarioDisponibleDAO implements HorarioDisponibleDAO {
    @Autowired
    private HorarioDisponibleDAO horarioDisponibleDAO;
    @Override
    public void saveHorarioDispo(HorarioDisponible horarioDisponible) {
        horarioDisponibleDAO.saveHorarioDispo(horarioDisponible);
    }

    @Override
    public List<HorarioDisponible> findAllHorarios() {
        return horarioDisponibleDAO.findAllHorarios();
    }

    @Override
    public void deleteHorarioDispo(Long id) {
        horarioDisponibleDAO.deleteHorarioDispo(id);
    }

    @Override
    public HorarioDisponible updateHorario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        return horarioDisponibleDAO.updateHorario(id, diaSemana, horaInicio, horaFin);
    }
}
