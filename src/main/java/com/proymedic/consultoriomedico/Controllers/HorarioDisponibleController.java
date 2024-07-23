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
import java.util.Optional;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHorario(@PathVariable Long id, @RequestBody HorarioDisponibleDTO horarioDisponibleDTO){
        if (id != null && id > 0 && horarioDisponibleDTO != null){
            Optional<HorarioDisponible> horarioDisponibleOptional = Optional.ofNullable(iHorarioDisponibleService.findById(id));

            if (horarioDisponibleOptional.isPresent()){
                HorarioDisponible horarioDisponible = horarioDisponibleOptional.get();
                Medico medico = iMedicoService.findById(horarioDisponibleDTO.getMedico());

                horarioDisponible.setDiaSemana(horarioDisponibleDTO.getDiaSemana());
                horarioDisponible.setHoraInicio(horarioDisponibleDTO.getHoraInicio());
                horarioDisponible.setHoraFin(horarioDisponibleDTO.getHoraFin());
                horarioDisponible.setMedico(medico);

                iHorarioDisponibleService.saveHorarioDispo(horarioDisponible);

                return ResponseEntity.ok("Horario actualizado con exito!!");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (id != null && id > 0){
            iHorarioDisponibleService.deleteHorarioDispo(id);
            return ResponseEntity.noContent().build();
        }else {
           return ResponseEntity.badRequest().body("Id incorrecto");
        }
    }
}
