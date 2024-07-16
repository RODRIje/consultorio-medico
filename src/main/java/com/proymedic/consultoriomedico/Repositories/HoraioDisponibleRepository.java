package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraioDisponibleRepository extends JpaRepository<HorarioDisponible, Long> {
}
