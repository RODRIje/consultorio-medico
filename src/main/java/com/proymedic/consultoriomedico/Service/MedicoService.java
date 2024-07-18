package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
    // Crear, actualizar, eliminar y listar médicos.

    // Crear Medico
    void saveMedico(Medico medico);

    // Actualizar Medico
    Medico updateMedico(Long id, Medico medico);

    // Eñiminar medico
    void deleteMedico(Long id);

    // Listar medicos
    List<Medico> findAllMedico();

}
