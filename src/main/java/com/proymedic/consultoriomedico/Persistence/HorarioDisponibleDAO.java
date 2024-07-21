package com.proymedic.consultoriomedico.Persistence;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;

import java.time.LocalTime;
import java.util.List;

public interface HorarioDisponibleDAO {
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
