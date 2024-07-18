package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.MedicoDTO;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medico")
public class MedicoController {
    @Autowired
    private IMedicoService iMedicoService;

    @GetMapping("/find")
    public List<Medico> findMedicos(){
       return iMedicoService.findAllMedico();
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardarMedico(@RequestBody MedicoDTO medicoDTO) throws URISyntaxException {
        Medico medico = new Medico();

        medico.setNombre(medicoDTO.getNombre());
        medico.setApellido(medicoDTO.getApellido());
        medico.setMatricula(medicoDTO.getMatricula());
        medico.setEmail(medicoDTO.getEmail());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        medico.setHorariosDisponibles(medicoDTO.getHorariosDisponibles());

        iMedicoService.saveMedico(medico);

        return ResponseEntity.created(new URI("/api/medico/save")).build();
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

   /* @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMedicoById(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO){

        if (id != null && id > 0 && medicoDTO != null){

        }
    }*/
}
