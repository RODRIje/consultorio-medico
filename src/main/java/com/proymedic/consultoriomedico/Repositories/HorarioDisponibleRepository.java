package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioDisponibleRepository extends JpaRepository<HorarioDisponible, Long> {
}
