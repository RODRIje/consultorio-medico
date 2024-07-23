package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
    // Crear, actualizar, eliminar y listar médicos.

    // Crear Medico
    void saveMedico(Medico medico);

    // Actualizar Medico
    void updateMedico(Long id, Medico medico);

    // Eñiminar medico
    Boolean deleteMedico(Long id);

    // Listar medicos
    List<Medico> findAllMedico();

    // Buscar medico por id
    Medico findById(Long id);

}
