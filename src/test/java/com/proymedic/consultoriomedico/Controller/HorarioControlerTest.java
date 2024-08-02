package com.proymedic.consultoriomedico.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.HorarioDisponibleRepository;
import com.proymedic.consultoriomedico.Service.impl.IHorarioDisponibleService;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class HorarioControlerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private IHorarioDisponibleService iHorarioDisponibleService;
    @MockBean
    private HorarioDisponibleRepository horarioDisponibleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @PostConstruct
    void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void testSaveHorario() throws Exception{
        // given
        Medico medico = Medico.builder()
                .nombre("Martin")
                .apellido("Faravelo")
                .especialidad("Clinico")
                .build();

        HorarioDisponible horarioNew = HorarioDisponible.builder()
                .id(1L)
                .medico(medico)
                .diaSemana("Lunes")
                .horaInicio(LocalTime.of(6,30))
                .horaFin(LocalTime.of(19,30))
                .build();
        given(iHorarioDisponibleService.guardarHorario(any(HorarioDisponible.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/api/horariodis/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horarioNew)));
        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diaSemana", is(horarioNew.getDiaSemana())))
                .andExpect(jsonPath("$.medico.nombre", is(horarioNew.getMedico().getNombre())))
                .andExpect(jsonPath("$.medico.apellido", is(horarioNew.getMedico().getApellido())))
                .andExpect(jsonPath("$.medico.especialidad", is(horarioNew.getMedico().getEspecialidad())))
                .andExpect(jsonPath("$.horaInicio", is("06:30:00")))
                .andExpect(jsonPath("$.horaFin", is("19:30:00")));
    }
    @Test
    void testListHorario()throws Exception{
        // given
        List<HorarioDisponible> listHorario = new ArrayList<>();
        listHorario.add(HorarioDisponible.builder().diaSemana("Viernes").horaInicio(LocalTime.of(14,30)).horaFin(LocalTime.of(22,30)).build());
        listHorario.add(HorarioDisponible.builder().diaSemana("Domingo").horaInicio(LocalTime.of(8,30)).horaFin(LocalTime.of(17,30)).build());
        given(iHorarioDisponibleService.findAllHorarios()).willReturn(listHorario);
        // when
        ResultActions response = mockMvc.perform(get("/api/horariodis/find"));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listHorario.size())));
    }

    @Test
    void testUpdateHorario()throws Exception{
        // given
        Medico medico1 = Medico.builder()
                .nombre("Martin")
                .apellido("Faravelo")
                .especialidad("Clinico")
                .build();
        Long idHorario = 1L;
        HorarioDisponible horarioPrimero = HorarioDisponible.builder()
                .medico(medico1)
                .diaSemana("Lunes")
                .horaInicio(LocalTime.of(6,30))
                .horaFin(LocalTime.of(19,30))
                .build();

        Medico medico2 = Medico.builder()
                .nombre("Pepe")
                .apellido("Moreta")
                .especialidad("Cardiocirujano")
                .build();

        HorarioDisponible horarioActualizado = HorarioDisponible.builder()
                .medico(medico2)
                .diaSemana("Miercoles")
                .horaInicio(LocalTime.of(14,30))
                .horaFin(LocalTime.of(23,30))
                .build();
        given(iHorarioDisponibleService.findById(idHorario)).willReturn(horarioPrimero);
        given(iHorarioDisponibleService.actHorario(any(HorarioDisponible.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/horariodis/update/{id}", idHorario)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horarioActualizado)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.diaSemana", is(horarioActualizado.getDiaSemana())))
                .andExpect(jsonPath("$.medico.nombre", is(horarioActualizado.getMedico().getNombre())))
                .andExpect(jsonPath("$.medico.apellido", is(horarioActualizado.getMedico().getApellido())))
                .andExpect(jsonPath("$.medico.especialidad", is(horarioActualizado.getMedico().getEspecialidad())))
                .andExpect(jsonPath("$.horaInicio", is("14:30:00")))
                .andExpect(jsonPath("$.horaFin", is("23:30:00")));
    }
    @Test
    void testDeleteHorarioById() throws Exception{
        // given
        Long idHorario = 1L;
        willDoNothing().given(iHorarioDisponibleService).deleteHorarioDispo(idHorario);
        // when
        ResultActions response = mockMvc.perform(delete("/api/horariodis/delete/{id}", idHorario));
        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
