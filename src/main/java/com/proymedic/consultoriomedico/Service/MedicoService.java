package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Medico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
    // Crear, actualizar, eliminar y listar médicos.

    // Crear un medico y retornar el medico
    Medico guardarMedico (Medico medico);

    // Actualizar Medico
    void updateMedico(Long id, Medico medico);

    // Actualizar medico v2
    Medico ActuMedic(Medico medico);

    // Eñiminar medico
    void deleteMedico(Long id);

    // Listar medicos
    List<Medico> findAllMedico();

    // Buscar medico por id
    Medico findById(Long id);

    // Crear metodo para buscar medicos por su especialidad
    List<Medico> findMedicoByEspecialidad(String especialidad);

    // Crear metodo para buscar medico por nombre
    List<Medico> findMedicoByname(String nombre);
}
