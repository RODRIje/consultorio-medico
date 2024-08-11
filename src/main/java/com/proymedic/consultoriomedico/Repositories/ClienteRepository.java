package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Crear metodo para traer todos los clientes que tengan obra social
    // Crear metodo para traer todos los clientes que NO tengan obra social
    // Crear metodo para traer todos los clientes que X obra social
    // Crear metodo para traer clientes por su nombre
}
