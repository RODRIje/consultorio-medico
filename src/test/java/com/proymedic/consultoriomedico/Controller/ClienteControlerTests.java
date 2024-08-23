package com.proymedic.consultoriomedico.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.ClienteService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class ClienteControlerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private IClienteService iClienteService;
    @MockBean
    private ClienteRepository clienteRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @PostConstruct
    void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSaveCliente()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodrig@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
        given(iClienteService.guardarCliente(any(Cliente.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/api/cliente/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));
        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(cliente.getNombre())))
                .andExpect(jsonPath("$.apellido", is(cliente.getApellido())))
                .andExpect(jsonPath("$.email", is(cliente.getEmail())));
    }

    @Test
    void testListCliente()throws Exception{
        // given
        List<Cliente> list = new ArrayList<>();
        list.add(Cliente.builder().id(1L).nombre("Juan").apellido("Lopez").email("juanlo@gmail.com").obraSocial(false).nombreObraSocial("").build());
        list.add(Cliente.builder().id(2L).nombre("Martin").apellido("Vazquez").email("martovaz@gmail.com").obraSocial(true).nombreObraSocial("OSDE").build());
        list.add(Cliente.builder().id(3L).nombre("Lucas").apellido("Ocampos").email("luocampo@gmail.com").obraSocial(true).nombreObraSocial("SWISS MEDICAL").build());
        list.add(Cliente.builder().id(4L).nombre("Lara").apellido("Ramirez").email("larara@gmail.com").obraSocial(true).nombreObraSocial("IOMA").build());
        given(iClienteService.findAllCliente()).willReturn(list);
        // when
        ResultActions response = mockMvc.perform(get("/api/cliente/find"));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void testUpdateCliente()throws Exception{
        // given
        Long idCliente = 5L;
        Cliente clientePrimero = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodrig@gmail.com")
                .obraSocial(false)
                .nombreObraSocial(null)
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(1L)
                .nombre("Rodrigo Nicolas")
                .apellido("Gonzalez")
                .email("rodrig@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
        given(iClienteService.findById(idCliente)).willReturn(clientePrimero);
        given(iClienteService.ActuCliente(any(Cliente.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/cliente/update/{id}", idCliente)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteActualizado)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(clienteActualizado.getNombre())))
                .andExpect(jsonPath("$.apellido",is(clienteActualizado.getApellido())))
                .andExpect(jsonPath("$.email",is(clienteActualizado.getEmail())))
                .andExpect(jsonPath("$.obraSocial", is(clienteActualizado.getObraSocial())))
                .andExpect(jsonPath("$.nombreObraSocial", is(clienteActualizado.getNombreObraSocial())));
    }

    @Test
    void testDeleteClienteById() throws Exception{
        // given
        Long idCliente = 1L;
        willDoNothing().given(iClienteService).deleteCliente(idCliente);
        // when
        ResultActions response = mockMvc.perform(delete("/api/cliente/delete/{id}", idCliente));
        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void testFindByName()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodrig@gmail.com")
                .obraSocial(false)
                .nombreObraSocial(null)
                .build();

        List<Cliente> clienteList = Collections.singletonList(cliente);
        given(iClienteService.findClienteByName(cliente.getNombre()))
                .willReturn(clienteList);
        // when
        ResultActions response = mockMvc.perform(get("/api/cliente/findbyname")
                .param("nombre", cliente.getNombre())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre",is(cliente.getNombre())))
                .andExpect(jsonPath("$[0].apellido",is(cliente.getApellido())))
                .andExpect(jsonPath("$[0].email",is(cliente.getEmail())))
                .andExpect(jsonPath("$[0].obraSocial",is(cliente.getObraSocial())))
                .andExpect(jsonPath("$[0].nombreObraSocial",is(cliente.getNombreObraSocial())));
    }

    @Test
    void testFindObraSocialTrue()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .email("rodrig@gmail.com")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();
        List<Cliente> clienteList = Collections.singletonList(cliente);
        given(iClienteService.findClienteTrueObraSocial(cliente.getObraSocial()))
                .willReturn(clienteList);
        // when
        ResultActions response = mockMvc.perform(get("/api/cliente/findobrasocialtrue")
                .param("obraSocial", String.valueOf(true))
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre",is(cliente.getNombre())))
                .andExpect(jsonPath("$[0].apellido",is(cliente.getApellido())))
                .andExpect(jsonPath("$[0].nombreObraSocial",is(cliente.getNombreObraSocial())));
    }

    @Test
    void testFindMismaObraSocial()throws Exception{
            // given
            Cliente cliente1 = Cliente.builder()
                    .nombre("Rodrigo")
                    .apellido("Gonzalez")
                    .email("rodrig@gmail.com")
                    .obraSocial(true)
                    .nombreObraSocial("IOMA")
                    .build();

            Cliente cliente2 = Cliente.builder()
                    .nombre("Ana")
                    .apellido("Lopez")
                    .email("ana@gmail.com")
                    .obraSocial(true)
                    .nombreObraSocial("IOMA")
                    .build();

            Cliente cliente3 = Cliente.builder()
                    .nombre("Carlos")
                    .apellido("Perez")
                    .email("carlos@gmail.com")
                    .obraSocial(true)
                    .nombreObraSocial("OSDE")
                    .build();

            List<Cliente> clienteList = Arrays.asList(cliente1, cliente2);
            given(iClienteService.findClienteMismaObraSocial("IOMA"))
                    .willReturn(clienteList);

            // when
            ResultActions response = mockMvc.perform(get("/api/cliente/findmismaobrasocial")
                    .param("nombreObraSocial", "IOMA")
                    .contentType(MediaType.APPLICATION_JSON));

            // then
            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", is(2)))
                    .andExpect(jsonPath("$[0].nombre", is(cliente1.getNombre())))
                    .andExpect(jsonPath("$[0].apellido", is(cliente1.getApellido())))
                    .andExpect(jsonPath("$[0].nombreObraSocial", is(cliente1.getNombreObraSocial())))
                    .andExpect(jsonPath("$[1].nombre", is(cliente2.getNombre())))
                    .andExpect(jsonPath("$[1].apellido", is(cliente2.getApellido())))
                    .andExpect(jsonPath("$[1].nombreObraSocial", is(cliente2.getNombreObraSocial())));
        }


}
