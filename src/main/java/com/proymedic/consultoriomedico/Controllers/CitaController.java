package com.proymedic.consultoriomedico.Controllers;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cita")
public class CitaController {
    @Autowired
    private ICitaService iCitaService;

    @GetMapping("/find")
    public List<Cita> findall(){
        return iCitaService.findAllCita();
    }
}
