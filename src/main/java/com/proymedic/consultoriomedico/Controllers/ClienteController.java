package com.proymedic.consultoriomedico.Controllers;


import com.proymedic.consultoriomedico.Controllers.dto.ClienteDTO;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
