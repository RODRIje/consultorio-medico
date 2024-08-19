package com.proymedic.consultoriomedico.Controllers;


import com.proymedic.consultoriomedico.Controllers.dto.ClienteDTO;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
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
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/find")
    public List<Cliente> findAll(){
        return iClienteService.findAllCliente();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente){
        Cliente savedCliente = iClienteService.guardarCliente(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateClienteById(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){

        if (id != null && id > 0 && clienteDTO != null){
            Optional<Cliente> clienteOptional = Optional.ofNullable(iClienteService.findById(id));

            if (clienteOptional.isPresent()){
                Cliente clienteUpdate = clienteOptional.get();

                clienteUpdate.setNombre(clienteDTO.getNombre());
                clienteUpdate.setApellido(clienteDTO.getApellido());
                clienteUpdate.setEmail(clienteDTO.getEmail());
                clienteUpdate.setObraSocial(clienteDTO.getObraSocial());
                clienteUpdate.setNombreObraSocial(clienteDTO.getNombreObraSocial());

                iClienteService.updateCliente(id, clienteUpdate);

                return ResponseEntity.ok(clienteUpdate); // Devolver el objeto Cliente actualizado
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClienteById(@PathVariable Long id){
        if (id != null){
            iClienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.badRequest().body("Id incorrecto");
        }
    }

    @GetMapping("/findobrasocialtrue")
    public ResponseEntity<?> findObraSocialTrue(@Param("obraSocial") Boolean obraSocial){
        if (obraSocial == null){
            return ResponseEntity.badRequest().body(null);
        }
        List<Cliente> clienteList = iClienteService.findClienteTrueObraSocial(obraSocial);
        if (clienteList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(clienteList);
    }

    @GetMapping("/findmismaobrasocial")
    public ResponseEntity<?>  findMismaObraSocial(@Param("nombreObraSocial") String nombreObraSocial){
       if (nombreObraSocial == null || nombreObraSocial.isEmpty()){
           return ResponseEntity.badRequest().body(null);
       }
       List<Cliente> clienteList = iClienteService.findClienteMismaObraSocial(nombreObraSocial);
       if (clienteList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       return ResponseEntity.ok(clienteList);
    }

    @GetMapping("/findbyname")
    public ResponseEntity<?> findByName(@Param("nombre") String nombre){
        if (nombre == null || nombre.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        List<Cliente> clienteList = iClienteService.findClienteByName(nombre);
        if (clienteList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(clienteList);
    }
}
