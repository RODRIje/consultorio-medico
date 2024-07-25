package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

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

}
