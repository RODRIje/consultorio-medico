package com.proymedic.consultoriomedico.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Service.MedicoService;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
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

@SpringBootTest
public class MedicoControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private MedicoService medicoService;
    @MockBean
    private ICitaService iCitaService;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private IMedicoService iMedicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @PostConstruct
     void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSaveMedico() throws Exception {
        // given
        Medico medico = Medico.builder()
                .id(1L)
                .nombre("Rene")
                .apellido("Favaloro")
                .especialidad("Clinico")
                .email("renefava@gmail.com")
                .build();

        given(medicoService.guardarMedico(any(Medico.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/medico/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated());
    }

}

