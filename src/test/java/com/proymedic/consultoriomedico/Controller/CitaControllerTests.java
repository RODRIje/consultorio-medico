package com.proymedic.consultoriomedico.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
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

@SpringBootTest
public class CitaControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ICitaService iCitaService;
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
}
