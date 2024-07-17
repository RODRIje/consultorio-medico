package com.proymedic.consultoriomedico.Controllers.dto;

import com.proymedic.consultoriomedico.Entities.Medico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HorarioDisponibleDTO {
    private Long id;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Medico medico;
}
