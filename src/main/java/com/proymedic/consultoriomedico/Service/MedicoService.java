package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Medico;

import java.util.List;

public interface MedicoService {
    // Crear, actualizar, eliminar y listar médicos.

    // Crear Medico
    void saveMedico(Medico medico);

    // Actualizar Medico
    void updateMedico(Long id);

    // Eñiminar medico
    void deleteMedico(Long id);

    // Listar medicos
    List<Medico> findAllMedico();

}
