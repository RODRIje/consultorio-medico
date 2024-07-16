package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
