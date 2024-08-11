package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Crear metodo para buscar medicos por su especialidad
    @Query("SELECT m FROM Medico m WHERE m.especialidad = :especialidad")
    List<Medico> findMedicoByEspecialidad(@Param("especialidad")String especialidad);

    // Crear metodo para buscar medico por nombre
    @Query("SELECT m FROM Medico m WHERE m.nombre = :nombre")
    List<Medico> findMedicoByname(@Param("nombre") String nombre);
}
