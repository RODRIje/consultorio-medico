package com.proymedic.consultoriomedico.Controllers;


import com.proymedic.consultoriomedico.Controllers.dto.ClienteDTO;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> saveCliente(@RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setObraSocial(clienteDTO.getObraSocial());
        cliente.setNombreObraSocial(clienteDTO.getNombreObraSocial());

        iClienteService.saveCliente(cliente);

        return ResponseEntity.created(new URI("/api/cliente/save")).build();
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

                return ResponseEntity.ok("Cliente actualizado");
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
