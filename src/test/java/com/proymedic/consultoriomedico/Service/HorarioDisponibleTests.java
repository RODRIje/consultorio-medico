package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Repositories.HorarioDisponibleRepository;
import com.proymedic.consultoriomedico.Service.impl.IHorarioDisponibleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HorarioDisponibleTests {
    @Mock
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @InjectMocks
    private IHorarioDisponibleService horarioDisponibleService;

    private Medico medico;
    private HorarioDisponible horarioDisponibleFijo;

    @BeforeEach
    void setup(){
        medico = new Medico();
        medico.setNombre("Lautaro");
        medico.setApellido("Martinez");

        horarioDisponibleFijo = HorarioDisponible.builder()
                .id(1L)
                .medico(medico)
                .diaSemana("Martes")
                .horaFin(LocalTime.of(18,30))
                .horaInicio(LocalTime.of(6,30))
                .build();
    }

    @DisplayName("Test para guardar un horario")
    @Test
    void testSaveHorario(){
        // given
        given(horarioDisponibleRepository.save(horarioDisponibleFijo)).willReturn(horarioDisponibleFijo);
        // when
        HorarioDisponible horarioSave = horarioDisponibleService.guardarHorario(horarioDisponibleFijo);
        // then
        assertThat(horarioSave).isNotNull();
    }

    @DisplayName("Test para traer todos los horarios")
    @Test
    void testFindAllHorario(){
        // given
        given(horarioDisponibleRepository.findAll()).willReturn(List.of(horarioDisponibleFijo));
        // when
        List<HorarioDisponible> list = horarioDisponibleService.findAllHorarios();
        // then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("Test para buscar un horario por id")
    @Test
    void testFindHorarioById(){
        // given
        given(horarioDisponibleRepository.findById(1L)).willReturn(Optional.of(horarioDisponibleFijo));
        // when
        HorarioDisponible horarioDisponible = horarioDisponibleService.findById(horarioDisponibleFijo.getId());
        // then
        assertThat(horarioDisponible).isNotNull();
        assertThat(horarioDisponible.getId()).isEqualTo(1L);
    }

    @DisplayName("Test para eliminar un horario por id")
    @Test
    void testDeleteHorarioById(){
        // given
        Long horarioId = 5L;
        willDoNothing().given(horarioDisponibleRepository).deleteById(horarioId);
        // when
        horarioDisponibleService.deleteHorarioDispo(horarioId);
        // then
        verify(horarioDisponibleRepository, times(1)).deleteById(horarioId);
    }
}
