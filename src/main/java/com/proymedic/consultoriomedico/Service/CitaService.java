package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Crear cita y retornar cita
    Cita guardarCita(Cita cita);

    // Buscar cita por medico
    List<Cita> findByMedico(String medico);

    // Buscar cita por cliente
    List<Cita> findByCliente(String cliente);
}
