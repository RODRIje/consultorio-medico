package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cita;

import java.util.List;

public interface CitaService {
    // Crear, actualizar, cancelar y listar citas.

    // Crear Cita
    void saveCita(Cita cita);

    // Actualizar Cita
    Cita updateCita(Long id);

    // Cancelar/eliminar cita
    void deleteCita(Long id);

    // Listar todas las citas
    List<Cita> findAllCita();
}
