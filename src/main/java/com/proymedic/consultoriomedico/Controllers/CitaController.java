package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Controllers.dto.CitaDTO;
import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
    public ResponseEntity<?> saveCita(@RequestBody CitaDTO citaDTO) throws URISyntaxException {
        Cita citaActualizada = new Cita();

        Medico medico = iMedicoService.findById(citaDTO.getMedico());
        Cliente cliente = iClienteService.findById(citaDTO.getCliente());

        citaActualizada.setCliente(cliente);
        citaActualizada.setMedico(medico);
        citaActualizada.setHora(citaDTO.getHora());
        citaActualizada.setFecha(citaDTO.getFecha());
        citaActualizada.setObservaciones(citaDTO.getObservaciones());

        iCitaService.saveCita(citaActualizada);

        return ResponseEntity.created(new URI("/api/cita/save")).build();
    }
}