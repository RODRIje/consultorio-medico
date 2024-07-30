package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.MedicoDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medico")
public class MedicoController {
    @Autowired
    private IMedicoService iMedicoService;

    @GetMapping("/find")
    public List<Medico> findMedicos(){
       return iMedicoService.findAllMedico();
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveMedico(@RequestBody Medico medico){
        Medico savedMedico = iMedicoService.guardarMedico(medico);
        return new ResponseEntity<>(savedMedico, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteMedicoById(@PathVariable Long id){
        if (id != null){
            iMedicoService.deleteMedico(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.badRequest().body("Id incorrecto");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMedicoById(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO) {

        if (id != null && id > 0 && medicoDTO != null) {
            Optional<Medico> medicoOptional = Optional.ofNullable(iMedicoService.findById(id));

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();

                medico.setNombre(medicoDTO.getNombre());
                medico.setApellido(medicoDTO.getApellido());
                medico.setEmail(medicoDTO.getEmail());
                medico.setEspecialidad(medicoDTO.getEspecialidad());
                medico.setMatricula(medicoDTO.getMatricula());

                // Limpiar y agregar los nuevos elementos a la colección
                medico.getHorariosDisponibles().clear();
                medicoDTO.getHorariosDisponibles().forEach(horarioDTO -> {
                    HorarioDisponible horario = new HorarioDisponible();
                    horario.setDiaSemana(horarioDTO.getDiaSemana());
                    horario.setHoraInicio(horarioDTO.getHoraInicio());
                    horario.setHoraFin(horarioDTO.getHoraFin());
                    medico.getHorariosDisponibles().add(horario);
                });

                iMedicoService.updateMedico(id, medico);

                return ResponseEntity.ok("Médico actualizado");
            }
        }
        return ResponseEntity.badRequest().build();
    }


}
