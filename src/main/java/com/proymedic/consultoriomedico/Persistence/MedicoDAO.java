package com.proymedic.consultoriomedico.Persistence;

import com.proymedic.consultoriomedico.Entities.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoDAO {
    // Crear, actualizar, eliminar y listar médicos.

    // Crear Medico
    void saveMedico(Medico medico);

    // Actualizar Medico
    Medico updateMedico(Long id, Medico medico);

    // Eñiminar medico
    Boolean deleteMedico(Long id);

    // Listar medicos
    List<Medico> findAllMedico();

    // Listar medico por id
    Medico findById(Long id);
}
