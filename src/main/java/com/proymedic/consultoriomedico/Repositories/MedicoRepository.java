package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
