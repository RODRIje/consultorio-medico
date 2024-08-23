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

import java.util.*;

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

        given(iMedicoService.guardarMedico(any(Medico.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/medico/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre",is(medico.getNombre())))
                .andExpect(jsonPath("$.apellido",is(medico.getApellido())))
                .andExpect(jsonPath("$.email",is(medico.getEmail())));
    }
    @Test
    void testListMedicos() throws  Exception{
        // given
        List<Medico> listaMedicos = new ArrayList<>();
        listaMedicos.add(Medico.builder().nombre("Martin").apellido("Faravelo").especialidad("Clinico").build());
        listaMedicos.add(Medico.builder().nombre("Paola").apellido("Garcia").especialidad("Pediatra").build());
        listaMedicos.add(Medico.builder().nombre("Rodrigo").apellido("Gonzalez").especialidad("Cirujano").build());
        given(iMedicoService.findAllMedico()).willReturn(listaMedicos);
        // when
        ResultActions response = mockMvc.perform(get("/api/medico/find"));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listaMedicos.size())));
    }

    @Test
    void testUpdateMedico() throws Exception{
        // given
        Long medicoId = 1L;
        Medico medicoInicio = Medico.builder()
                .nombre("Rene")
                .apellido("Favaloro")
                .especialidad("Clinico")
                .horariosDisponibles(new ArrayList<>()) // Inicializar lista vacía
                .email("renefava@gmail.com")
                .build();

        Medico medicoUpdate = Medico.builder()
                .nombre("Rene Geronimo")
                .apellido("Favaloro")
                .especialidad("Cardiocirujano")
                .horariosDisponibles(new ArrayList<>()) // Inicializar lista vacía
                .email("renefava@gmail.com")
                .build();

        given(iMedicoService.findById(medicoId)).willReturn(medicoInicio);
        given(iMedicoService.ActuMedic(any(Medico.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/medico/update/{id}",medicoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicoUpdate)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(medicoUpdate.getNombre())))
                .andExpect(jsonPath("$.apellido",is(medicoUpdate.getApellido())))
                .andExpect(jsonPath("$.especialidad",is(medicoUpdate.getEspecialidad())));

    }

    @Test
    void testEliminarMedico() throws Exception{
        //given
        Long MedicoId = 1L;
        willDoNothing().given(iMedicoService).deleteMedico(MedicoId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/medico/delete/{id}",MedicoId));

        //then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void testFindByEspecialidad()throws Exception{
        // given
        Medico medico = Medico.builder()
                .id(1L)
                .nombre("Rene")
                .apellido("Favaloro")
                .especialidad("Clinico")
                .email("renefava@gmail.com")
                .build();

        List<Medico> medicoList = Collections.singletonList(medico);

        given(iMedicoService.findMedicoByEspecialidad(medico.getEspecialidad()))
                .willReturn(medicoList);
        // when
        ResultActions response = mockMvc.perform(get("/api/medico/findespecialidad")
                .param("especialidad", medico.getEspecialidad())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre", is(medico.getNombre())))
                .andExpect(jsonPath("$[0].apellido", is(medico.getApellido())))
                .andExpect(jsonPath("$[0].especialidad", is(medico.getEspecialidad())))
                .andExpect(jsonPath("$[0].email", is(medico.getEmail())));
    }

    @Test
    void testFindbyName()throws Exception{
        // given
        Medico medico = Medico.builder()
                .nombre("Rodrigo")
                .apellido("Gonzalez")
                .especialidad("Cardiocirujano")
                .email("rodrimedico@gmail.com")
                .matricula("123ABC456")
                .build();

        Medico medico2 = Medico.builder()
                .nombre("Martin")
                .apellido("Faravelo")
                .especialidad("Clinico")
                .email("martinmedico@gmail.com")
                .matricula("789DEF012")
                .build();

        List<Medico> medicoList = Arrays.asList(medico, medico2);
        given(iMedicoService.findMedicoByname(medico2.getNombre()))
                .willReturn(Collections.singletonList(medico2));
        // when
        ResultActions response = mockMvc.perform(get("/api/medico/findname")
                .param("nombre", medico2.getNombre())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre", is(medico2.getNombre())))
                .andExpect(jsonPath("$[0].apellido", is(medico2.getApellido())))
                .andExpect(jsonPath("$[0].especialidad", is(medico2.getEspecialidad())))
                .andExpect(jsonPath("$[0].email", is(medico2.getEmail())))
                .andExpect(jsonPath("$[0].matricula",is(medico2.getMatricula())));
    }

}

