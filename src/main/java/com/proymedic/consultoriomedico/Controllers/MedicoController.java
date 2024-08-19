package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.MedicoDTO;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

                return ResponseEntity.ok(medico); // Devolver el objeto Medico actualizado
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/findespecialidad")
    public ResponseEntity<?> findByEspecialidad(@Param("especialidad") String especialidad){
        if (especialidad == null || especialidad.isEmpty()){
            return ResponseEntity.badRequest().body(null); // Retorna 400 si la especialidad es null o esta vacia
        }
        List<Medico> medicos = iMedicoService.findMedicoByEspecialidad(especialidad);
        if (medicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 si no se encuentran medicos
        }
        return ResponseEntity.ok(medicos); // Retorna 200 si encuentra la lista de medicos
    }

    @GetMapping("/findname")
    public ResponseEntity<?> findByName(@Param("nombre") String nombre){
        if (nombre == null || nombre.isEmpty()){
            return ResponseEntity.badRequest().body(null); // 400 si el nombre es null o esta vacio
        }
        List<Medico> medicoByname = iMedicoService.findMedicoByname(nombre);
        if (medicoByname.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 si no se encuentran medicos
        }
        return ResponseEntity.ok(medicoByname); // 200 si encuentra la lista de medicos
    }
}
