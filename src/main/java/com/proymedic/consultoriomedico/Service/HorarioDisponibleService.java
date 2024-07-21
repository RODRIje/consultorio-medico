package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;

import java.time.LocalTime;
import java.util.List;

public interface HorarioDisponibleService {
    //AVERIGUAR QUE METODOS UTILIZAR

    // Crear (save) el horaioDisponible del medico
    void saveHorarioDispo(HorarioDisponible horarioDisponible);

    // Traer todos los horariosDisponible de todos los medico
    List<HorarioDisponible> findAllHorarios();

    // Eliminar un horarioDisponible por ID
    void deleteHorarioDispo(Long id);

    // Actualizar un horarioDisponible
    HorarioDisponible updateHorario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin);
}
