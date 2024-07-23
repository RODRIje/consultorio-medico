package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cita;

import java.util.List;
import java.util.Optional;

public interface CitaService {
    // Crear, actualizar, cancelar y listar citas.

    // Crear Cita
    void saveCita(Cita cita);

    // Actualizar Cita
    void updateCita(Long id, Cita cita);

    // Cancelar/eliminar cita
    void deleteCita(Long id);

    // Listar todas las citas
    List<Cita> findAllCita();

    // Buscar cita por id
    Cita findById(Long id);
}
