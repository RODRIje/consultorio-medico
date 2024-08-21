package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTests {
    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente clienteFijo;

    @BeforeEach
    void setup(){
         clienteFijo = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodri@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
    }

    @DisplayName("Test para guardar cliente")
    @Test
    void testGuardarCliente(){
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodri@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
        // when
        Cliente clienteSave = clienteRepository.save(cliente);
        // then
        assertThat(clienteSave).isNotNull();
        assertThat(clienteSave.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar clientes")
    @Test
    void testFindAllClientes(){
        // given
        Cliente cliente1 = Cliente.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juanpe@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .build();
        clienteRepository.save(cliente1);
        clienteRepository.save(clienteFijo);

        // when
        List<Cliente> clienteList = clienteRepository.findAll();
        // then
        assertThat(clienteList).isNotNull();
        assertThat(clienteList.size()).isEqualTo(8);
    }

    @DisplayName("Test para mostrar un cliente por id")
    @Test
    void testListClineteById(){
        // given
        clienteRepository.save(clienteFijo);
        // when
        Cliente cliente = clienteRepository.findById(clienteFijo.getId()).get();
        // then
        assertThat(cliente).isNotNull();
    }

    @DisplayName("Test para actualizar un cliente")
    @Test
    void testUpdateCliente(){
        // given
        clienteRepository.save(clienteFijo);
        // when
        Cliente clienteGuardado = clienteRepository.findById(clienteFijo.getId()).get();
        clienteGuardado.setNombre("Ricardo");
        clienteGuardado.setApellido("Iorio");
        clienteGuardado.setEmail("ricardero@gmail.com");
        clienteGuardado.setObraSocial(true);
        clienteGuardado.setNombreObraSocial("SWISS MEDICAL");
        Cliente clienteUpdate = clienteRepository.save(clienteGuardado);
        // then
        assertThat(clienteUpdate.getNombre()).isEqualTo("Ricardo");
        assertThat(clienteUpdate.getApellido()).isEqualTo("Iorio");
        assertThat(clienteUpdate.getEmail()).isEqualTo("ricardero@gmail.com");
        assertThat(clienteUpdate.getObraSocial()).isEqualTo(true);
        assertThat(clienteUpdate.getNombreObraSocial()).isEqualTo("SWISS MEDICAL");
    }

    @DisplayName("Test para eliminar un cliente por id")
    @Test
    void testDeleteClienteById(){
        // given
        clienteRepository.save(clienteFijo);
        // when
        clienteRepository.deleteById(clienteFijo.getId());
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteFijo.getId());
        // then
        assertThat(clienteOptional).isEmpty();
    }
}
