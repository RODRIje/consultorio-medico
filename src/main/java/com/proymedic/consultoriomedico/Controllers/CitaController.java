package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.CitaDTO;
import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cita")
public class CitaController {
    @Autowired
    private ICitaService iCitaService;

    @Autowired
    private IClienteService iClienteService; // Servicio para manejar clientes

    @Autowired
    private IMedicoService iMedicoService; // Servicio para manejar médicos

    @GetMapping("/find")
    public List<Cita> findall() {
        return iCitaService.findAllCita();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCita(@RequestBody Cita cita) throws URISyntaxException {
        Cita citaSaved = iCitaService.guardarCita(cita);
        return new ResponseEntity<>(citaSaved, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO){

        if (id == null || id <= 0 || citaDTO == null) {
            return ResponseEntity.badRequest().body("ID inválido o datos incompletos");
        }
            Optional<Cita> citaOptional = Optional.ofNullable(iCitaService.findById(id));

            if (!citaOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
            }
                Cita citaUpdate = citaOptional.get();
                Medico medico = iMedicoService.findById(citaDTO.getMedico());
                Cliente cliente = iClienteService.findById(citaDTO.getCliente());

            if (medico == null) {
                return ResponseEntity.badRequest().body("Medico no encontrado");
            }
            if (cliente == null) {
                return ResponseEntity.badRequest().body("Cliente no encontrado");
            }

                citaUpdate.setObservaciones(citaDTO.getObservaciones());
                citaUpdate.setHora(citaDTO.getHora());
                citaUpdate.setFecha(citaDTO.getFecha());
                citaUpdate.setCliente(cliente);
                citaUpdate.setMedico(medico);

                iCitaService.guardarCita(citaUpdate);

                return ResponseEntity.ok(citaUpdate); // Devolver el objeto Cita actualizado
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCitaById(@PathVariable Long id){
        if (id != null){
            iCitaService.deleteCita(id);
            return ResponseEntity.noContent().build();
        }else
            return ResponseEntity.badRequest().body("Id incorrecto");
    }
}