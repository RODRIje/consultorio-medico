package com.proymedic.consultoriomedico.Controllers.dto;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitaDTO {
    private Long cliente;
    private Long medico;
    private LocalDate fecha; // YYYY:MM:DD
    private LocalTime hora; // HH:MM
    private String observaciones;
}
