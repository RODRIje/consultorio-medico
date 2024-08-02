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
    void updateHorario(Long id, HorarioDisponible horarioDisponible);

    // Actualizar horario v2
    HorarioDisponible actHorario(HorarioDisponible horarioDisponible);

    // Traer un horario por id
    HorarioDisponible findById(Long id);

    // Guardar un horario y retornar el horario
    HorarioDisponible guardarHorario(HorarioDisponible horarioDisponible);
}
