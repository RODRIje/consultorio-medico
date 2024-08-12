package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Crear metodo para traer todos los clientes que tengan o no tengan obra social
    @Query("SELECT c FROM Cliente c WHERE c.obraSocial = :obraSocial")
    List<Cliente> findClienteTrueObraSocial(@Param("obraSocial") Boolean obraSocial);
    // Crear metodo para traer todos los clientes que tengan X obra social
    @Query("SELECT c FROM Cliente c WHERE c.nombreObraSocial = :nombreObraSocial")
    List<Cliente> findClienteMismaObraSocial(@Param("nombreObraSocial") String nombreObraSocial);
    // Crear metodo para traer clientes por su nombre
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    List<Cliente> findClienteByName(@Param("nombre") String nombre);
}
