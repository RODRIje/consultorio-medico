package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioDisponibleRepository extends JpaRepository<HorarioDisponible, Long> {
   /* @Query
    public HorarioDisponible update(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFin);*/
}
