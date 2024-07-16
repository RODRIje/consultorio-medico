package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
