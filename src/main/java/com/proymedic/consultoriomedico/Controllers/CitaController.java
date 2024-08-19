package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.CitaDTO;
import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    public List<Cita> findAll() {
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

    @GetMapping("/findbymedico")
    public ResponseEntity<?> findByMedico(@Param("medico") String medico){
        if (medico == null || medico.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        List<Cita> citaList = iCitaService.findByMedico(medico);
        if (citaList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(citaList);
    }

    @GetMapping("/findbycliente")
    public ResponseEntity<?> findByCliente(@Param("cliente") String cliente){
        if (cliente ==  null || cliente.isEmpty()){
            return ResponseEntity.badRequest().body("El cliente es null");
        }
        List<Cita> citaList = iCitaService.findByCliente(cliente);
        if (citaList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista de citas esta vacia");
        }
        return ResponseEntity.ok(citaList);
    }
}