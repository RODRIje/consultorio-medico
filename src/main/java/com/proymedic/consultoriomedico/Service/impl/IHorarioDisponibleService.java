package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Persistence.impl.IHorarioDisponibleDAO;
import com.proymedic.consultoriomedico.Persistence.impl.IMedicoDAO;
import com.proymedic.consultoriomedico.Service.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class IHorarioDisponibleService implements HorarioDisponibleService {
    @Autowired
    private IHorarioDisponibleDAO iHorarioDisponibleDAO;

    @Autowired
    private IMedicoDAO iMedicoDAO;

    @Override
    public void saveHorarioDispo(HorarioDisponible horarioDisponible) {
        iHorarioDisponibleDAO.saveHorarioDispo(horarioDisponible);
    }

    @Override
    public List<HorarioDisponible> findAllHorarios() {
        return iHorarioDisponibleDAO.findAllHorarios();
    }

    @Override
    public void deleteHorarioDispo(Long id) {
        iHorarioDisponibleDAO.deleteHorarioDispo(id);
    }

    @Override
    public HorarioDisponible updateHorario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        return null;
    }
}
