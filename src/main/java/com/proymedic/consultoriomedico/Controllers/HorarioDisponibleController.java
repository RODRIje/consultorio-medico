package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.HorarioDisponibleDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;

import com.proymedic.consultoriomedico.Service.impl.IHorarioDisponibleService;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/horariodis")
public class HorarioDisponibleController {
    @Autowired
    private IHorarioDisponibleService iHorarioDisponibleService;

    @Autowired
    private IMedicoService iMedicoService;

    @GetMapping("/find")
    public List<HorarioDisponible> findAll(){
        return iHorarioDisponibleService.findAllHorarios();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody HorarioDisponibleDTO horarioDisponibleDTO) throws URISyntaxException {

        HorarioDisponible horario = new HorarioDisponible();
        Medico medico = iMedicoService.findById(horarioDisponibleDTO.getMedico());

        horario.setDiaSemana(horarioDisponibleDTO.getDiaSemana());
        horario.setHoraInicio(horarioDisponibleDTO.getHoraInicio());
        horario.setHoraFin(horarioDisponibleDTO.getHoraFin());
        horario.setMedico(medico);

        iHorarioDisponibleService.saveHorarioDispo(horario);

        return ResponseEntity.created(new URI("/api/horariodis/save")).build();
    }
}
