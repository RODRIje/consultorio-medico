package com.proymedic.consultoriomedico.Controllers.dto;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String email;
    private String matricula;
    private List<HorarioDisponible> horariosDisponibles = new ArrayList<>();
}
