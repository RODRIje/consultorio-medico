package com.proymedic.consultoriomedico.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proymedic.consultoriomedico.Controllers.dto.CitaDTO;
import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CitaControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ICitaService iCitaService;
    @MockBean
    private IMedicoService iMedicoService;
    @MockBean
    private IClienteService iClienteService;
    @MockBean
    private CitaRepository citaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @PostConstruct
    void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSaveCita()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();

        Medico medico = Medico.builder()
                .especialidad("Cardiocirujano")
                .nombre("Rene")
                .apellido("Favaloro")
                .build();

        Cita cita = Cita.builder()
                .id(1L)
                .fecha(LocalDate.of(2024,10,21))
                .hora(LocalTime.of(14,30))
                .observaciones("El cliente debe volver el 27 de octubre")
                .medico(medico)
                .cliente(cliente)
                .build();
        given(iCitaService.guardarCita(any(Cita.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/api/cita/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)));
        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fecha", is("2024-10-21")))
                .andExpect(jsonPath("$.hora", is("14:30:00")))
                .andExpect(jsonPath("$.observaciones", is(cita.getObservaciones())))
                .andExpect(jsonPath("$.medico.nombre", is(cita.getMedico().getNombre())))
                .andExpect(jsonPath("$.medico.especialidad", is(cita.getMedico().getEspecialidad())))
                .andExpect(jsonPath("$.medico.apellido", is(cita.getMedico().getApellido())))
                .andExpect(jsonPath("$.cliente.nombre", is(cita.getCliente().getNombre())))
                .andExpect(jsonPath("$.cliente.apellido", is(cita.getCliente().getApellido())))
                .andExpect(jsonPath("$.cliente.obraSocial", is(cita.getCliente().getObraSocial())))
                .andExpect(jsonPath("$.cliente.nombreObraSocial", is(cita.getCliente().getNombreObraSocial())));
    }

    @Test
    void testListCita()throws Exception{
        // given
        List<Cita> list = new ArrayList<>();
        list.add(Cita.builder().fecha(LocalDate.of(2024,8,8)).hora(LocalTime.of(8,30)).build());
        list.add(Cita.builder().fecha(LocalDate.of(2024,9,8)).hora(LocalTime.of(8,30)).build());
        given(iCitaService.findAllCita()).willReturn(list);
        // when
        ResultActions response = mockMvc.perform(get("/api/cita/find"));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void testUpdateCita() throws Exception {
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();

        Medico medico = Medico.builder()
                .especialidad("Cardiocirujano")
                .nombre("Rene")
                .apellido("Favaloro")
                .build();

        Long idCita = 1L;
        Cita cita = Cita.builder()
                .fecha(LocalDate.of(2024,10,21))
                .hora(LocalTime.of(14,30))
                .observaciones("El cliente debe volver el 27 de octubre")
                .medico(medico)
                .cliente(cliente)
                .build();

        Cliente cliente2 = Cliente.builder()
                .id(2L)
                .nombre("Rodrigo Nicolas")
                .apellido("Gonzalez Garcia")
                .obraSocial(true)
                .nombreObraSocial("SWISS MEDICAL")
                .build();

        Medico medico2 = Medico.builder()
                .id(3L)
                .especialidad("Clinico")
                .nombre("Martin")
                .apellido("Faravelo")
                .build();

        CitaDTO citaUpdate = CitaDTO.builder()
                .fecha(LocalDate.of(2024,10,27))
                .hora(LocalTime.of(18,30))
                .observaciones("El cliente esta de alta")
                .medico(medico2.getId())
                .cliente(cliente2.getId())
                .build();

        given(iCitaService.findById(idCita)).willReturn(cita);
        given(iMedicoService.findById(anyLong())).willReturn(medico2); // Mockear el servicio del medico
        given(iClienteService.findById(anyLong())).willReturn(cliente2); // Mockear el servicio del cliente
        given(iCitaService.guardarCita(any(Cita.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/cita/update/{id}", idCita)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citaUpdate)));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fecha", is("2024-10-27")))
                .andExpect(jsonPath("$.hora", is("18:30:00")))
                .andExpect(jsonPath("$.observaciones", is(citaUpdate.getObservaciones())))
                .andExpect(jsonPath("$.medico.nombre", is(medico2.getNombre())))
                .andExpect(jsonPath("$.cliente.nombre", is(cliente2.getNombre())));

    }

    @Test
    void testDeleteCita()throws Exception{
        // given
        Long idCita = 1L;
        willDoNothing().given(iCitaService).deleteCita(idCita);
        // when
        ResultActions response = mockMvc.perform(delete("/api/cita/delete/{id}", idCita));
        // then
        response.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindCitaByMedico()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();

        Medico medico = Medico.builder()
                .especialidad("Cardiocirujano")
                .nombre("Rene")
                .apellido("Favaloro")
                .build();

        Cita cita = Cita.builder()
                .fecha(LocalDate.of(2024,10,21))
                .hora(LocalTime.of(14,30))
                .observaciones("El cliente debe volver el 27 de octubre")
                .medico(medico)
                .cliente(cliente)
                .build();
        List<Cita> citaList = Arrays.asList(cita);
        given(iCitaService.findByMedico(cita.getMedico().getNombre()))
                .willReturn(citaList);
        // when
        ResultActions response = mockMvc.perform(get("/api/cita/findbymedico")
                .param("medico", cita.getMedico().getNombre())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fecha", is("2024-10-21")))
                .andExpect(jsonPath("$[0].hora", is("14:30:00")))
                .andExpect(jsonPath("$[0].observaciones", is(cita.getObservaciones())))
                .andExpect(jsonPath("$[0].medico.nombre", is(cita.getMedico().getNombre())))
                .andExpect(jsonPath("$[0].cliente.nombre", is(cita.getCliente().getNombre())));
    }

    @Test
    void testFindCitaByCliente()throws Exception{
        // given
        Cliente cliente = Cliente.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .obraSocial(true)
                .nombreObraSocial("IOMA")
                .build();

        Medico medico = Medico.builder()
                .especialidad("Cardiocirujano")
                .nombre("Rene")
                .apellido("Favaloro")
                .build();

        Cita cita = Cita.builder()
                .fecha(LocalDate.of(2024,10,21))
                .hora(LocalTime.of(14,30))
                .observaciones("El cliente debe volver el 27 de octubre")
                .medico(medico)
                .cliente(cliente)
                .build();
        List<Cita> citaList = Arrays.asList(cita);
        given(iCitaService.findByCliente(cita.getCliente().getNombre()))
                .willReturn(citaList);
        // when
        ResultActions response = mockMvc.perform(get("/api/cita/findbycliente")
                .param("cliente", cita.getCliente().getNombre())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fecha", is("2024-10-21")))
                .andExpect(jsonPath("$[0].hora", is("14:30:00")))
                .andExpect(jsonPath("$[0].observaciones", is(cita.getObservaciones())))
                .andExpect(jsonPath("$[0].medico.nombre", is(cita.getMedico().getNombre())))
                .andExpect(jsonPath("$[0].cliente.nombre", is(cita.getCliente().getNombre())));

    }
}
