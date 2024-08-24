package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTests {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private IClienteService clienteService;

    private Cliente clienteFijo;

    @BeforeEach
    void setup(){
        clienteFijo = Cliente.builder()
                .id(1L)
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodri@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
    }

    @DisplayName("Test para guardar cliente")
    @Test
    void testSaveCliente(){
        // given
        given(clienteRepository.save(clienteFijo)).willReturn(clienteFijo);
        // when
        Cliente clienteSave = clienteRepository.save(clienteFijo);
        // then
        assertThat(clienteSave).isNotNull();
    }

    @DisplayName("Test para listar todos los clientes")
    @Test
    void testFindCliente(){
        // given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ramon")
                .apellido("Abila")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("wancho@gmail.com")
                .build();
        given(clienteRepository.findAll()).willReturn(List.of(cliente, clienteFijo));
        // when
        List<Cliente> clienteList = clienteService.findAllCliente();
        // then
        assertThat(clienteList).isNotNull();
        assertThat(clienteList.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListCollectionClienteEmpty(){
        // given
        Cliente cliente = Cliente.builder()
                .id(2L)
                .nombre("Ramon")
                .apellido("Abila")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("wancho@gmail.com")
                .build();
        given(clienteRepository.findAll()).willReturn(Collections.emptyList());
        // when
        List<Cliente> list = clienteService.findAllCliente();
        // then
        assertThat(list).isEmpty();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("Test para retornar un cliente por id")
    @Test
    void testFindClienteById(){
        // given
        given(clienteRepository.findById(1L)).willReturn(Optional.of(clienteFijo));
        // when
        Cliente clienteGuardado = clienteService.findById(clienteFijo.getId());
        // then
        assertThat(clienteGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un cliente")
    @Test
    void testUpdateCliente(){
        // given
        given(clienteRepository.save(clienteFijo)).willReturn(clienteFijo);
        clienteFijo.setNombre("Nikolas");
        clienteFijo.setNombreObraSocial("OSPRERA");
        // when
        Cliente clienteUpdate = clienteService.guardarCliente(clienteFijo);
        // then
        assertThat(clienteUpdate.getNombre()).isEqualTo("Nikolas");
        assertThat(clienteUpdate.getNombreObraSocial()).isEqualTo("OSPRERA");

    }

    @DisplayName("Test para eliminar un cliente por id")
    @Test
    void testDeleteClienteById(){
        // given
        Long clienteId = 3L;
        willDoNothing().given(clienteRepository).deleteById(clienteId);
        // when
        clienteService.deleteCliente(clienteId);
        // then
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    @DisplayName("Test para buscar clientes con obra social")
    @Test
    void testFindClienteObraSocialTrue(){
        // given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ramon")
                .apellido("Abila")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("wancho@gmail.com")
                .build();

        List<Cliente> listObraTrue = Arrays.asList(cliente);
        given(clienteRepository.findClienteTrueObraSocial(true)).willReturn(listObraTrue);

        // when
        List<Cliente> listCliente = clienteService.findClienteTrueObraSocial(true);

        // then
        assertThat(listCliente).isNotNull();
        assertThat(listCliente).hasSize(1);
        assertThat(listCliente.get(0).getNombre()).isEqualTo("Ramon");
        assertThat(listCliente.get(0).getApellido()).isEqualTo("Abila");
        assertThat(listCliente.get(0).getObraSocial()).isTrue();
        assertThat(listCliente.get(0).getNombreObraSocial()).isEqualTo("OSDE");
    }

    @DisplayName("Test para traer clientes con la misma obra social")
    @Test
    void testFindClienteMismaObraSocial(){
        // given
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Ramon")
                .apellido("Abila")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("wancho@gmail.com")
                .build();

        Cliente cliente2 = Cliente.builder()
                .id(1L)
                .nombre("Roman")
                .apellido("Riquelme")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("romi@gmail.com")
                .build();

        List<Cliente> clienteMisma = Arrays.asList(cliente1, cliente2);
        given(clienteRepository.findClienteMismaObraSocial("OSDE")).willReturn(clienteMisma);

        // when
        List<Cliente> listCliente = clienteService.findClienteMismaObraSocial("OSDE");
        // then
        assertThat(listCliente).isNotNull();
        assertThat(listCliente).hasSize(2);
        assertThat(listCliente.get(0).getNombre()).isEqualTo("Ramon");
        assertThat(listCliente.get(0).getApellido()).isEqualTo("Abila");
        assertThat(listCliente.get(0).getObraSocial()).isTrue();
        assertThat(listCliente.get(0).getNombreObraSocial()).isEqualTo("OSDE");
        assertThat(listCliente.get(1).getNombre()).isEqualTo("Roman");
        assertThat(listCliente.get(1).getApellido()).isEqualTo("Riquelme");
        assertThat(listCliente.get(1).getObraSocial()).isTrue();
        assertThat(listCliente.get(1).getNombreObraSocial()).isEqualTo("OSDE");

    }

    @DisplayName("Test para buscar clientes por nombre")
    @Test
    void testFindClienteByName(){
        // given
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Roman")
                .apellido("Varela")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("vare@gmail.com")
                .build();

        Cliente cliente2 = Cliente.builder()
                .id(2L)
                .nombre("Roman")
                .apellido("Riquelme")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .email("romi@gmail.com")
                .build();

        List<Cliente> clienteListMismoNombre = Arrays.asList(cliente1, cliente2);
        given(clienteRepository.findClienteByName("Roman")).willReturn(clienteListMismoNombre);

        // when
        List<Cliente> listCliente = clienteService.findClienteByName("Roman");
        // then
        assertThat(listCliente).isNotNull();
        assertThat(listCliente).hasSize(2);
        assertThat(listCliente.get(0).getNombre()).isEqualTo("Roman");
        assertThat(listCliente.get(0).getApellido()).isEqualTo("Varela");
        assertThat(listCliente.get(1).getNombre()).isEqualTo("Roman");
        assertThat(listCliente.get(1).getApellido()).isEqualTo("Riquelme");
    }

    @DisplayName("Test para actualizar un cliente por id")
    @Test
    void testUpdateClienteById(){
        // given
        Long clienteId = 1L;

        Cliente clienteExistente = Cliente.builder()
                .nombre("Roman")
                .apellido("Varela")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .nombre("Roman")
                .apellido("Riquelme")
                .obraSocial(true)
                .nombreObraSocial("OSDE")
                .build();

        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(clienteExistente));
        given(clienteRepository.save(any(Cliente.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        clienteService.updateCliente(clienteId, clienteActualizado);

        // then
        assertThat(clienteActualizado.getNombre()).isEqualTo("Roman");
        assertThat(clienteActualizado.getApellido()).isEqualTo("Riquelme");
        assertThat(clienteActualizado.getNombreObraSocial()).isEqualTo("OSDE");
        assertThat(clienteActualizado.getObraSocial()).isEqualTo(true);
        verify(clienteRepository).save(clienteActualizado);
    }

}
