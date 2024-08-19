package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Crear metodo para buscar cita por medico
    @Query("SELECT c FROM Cita c WHERE c.medico.nombre = :medico")
    List<Cita> findByMedico(@Param("medico") String medico);

    // Crear meotod para buscar cita por cliente
    @Query("SELECT c FROM Cita c WHERE c.cliente.nombre = :cliente")
    List<Cita> findByCliente(@Param("cliente") String cliente);
}
