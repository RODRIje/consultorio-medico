package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.HorarioDisponible;
import com.proymedic.consultoriomedico.Entities.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HorarioDisponibleRepositoryTests {
    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    private HorarioDisponible horarioFijo;
    private Medico medicoFijo;

    @BeforeEach
    void setup(){
        medicoFijo = Medico.builder()
                .nombre("Martin")
                .apellido("Wanchope")
                .especialidad("Cirujano")
                .build();

        horarioFijo = HorarioDisponible.builder()
                .diaSemana("Miercoles")
                .horaInicio(LocalTime.of(15,00))
                .horaFin(LocalTime.of(18,30))
                .medico(medicoFijo)
                .build();
    }
    @DisplayName("Test para guardar un horario")
    @Test
    void TestSaveHorario(){
        // given
        Medico medicoSave = medicoRepository.save(medicoFijo);
        // when
        HorarioDisponible horarioSave = horarioDisponibleRepository.save(horarioFijo);
        // then
        assertThat(horarioSave).isNotNull();
        assertThat(horarioSave.getDiaSemana()).isEqualTo("Miercoles");
        assertThat(horarioSave.getHoraFin()).isEqualTo(LocalTime.of(18,30));
    }

    @DisplayName("Test para listar todos los horarios")
    @Test
    void testFindAllHorarios(){
        // given
        Medico medicoSave = medicoRepository.save(medicoFijo);
        HorarioDisponible horarioSave = horarioDisponibleRepository.save(horarioFijo);
        // when
        List<HorarioDisponible> list = horarioDisponibleRepository.findAll();
        // then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);
    }

    @DisplayName("Test para buscar un horario por id")
    @Test
    void testFindHorarioById(){
        // given
        Medico medicoSave = medicoRepository.save(medicoFijo);
        HorarioDisponible horarioSave = horarioDisponibleRepository.save(horarioFijo);
        // when
        HorarioDisponible horarioBuscado = horarioDisponibleRepository.findById(horarioFijo.getId()).get();
        // then
        assertThat(horarioBuscado).isNotNull();
        assertThat(horarioBuscado.getDiaSemana()).isEqualTo("Miercoles");
    }
    @DisplayName("Test para actualizar un horario")
    @Test
    void testUpdateHorario(){
        // given
        Medico medicoSave = medicoRepository.save(medicoFijo);
        HorarioDisponible horarioSave = horarioDisponibleRepository.save(horarioFijo);
        // when
        HorarioDisponible horarioGuardado = horarioDisponibleRepository.findById(horarioSave.getId()).get();
        horarioGuardado.setDiaSemana("Viernes");
        horarioGuardado.setHoraInicio(LocalTime.of(8,00));
        horarioGuardado.setHoraFin(LocalTime.of(17,00));
        HorarioDisponible horarioUpdate = horarioDisponibleRepository.save(horarioGuardado);
        // then
        assertThat(horarioUpdate).isNotNull();
        assertThat(horarioUpdate.getDiaSemana()).isEqualTo("Viernes");
        assertThat(horarioUpdate.getHoraFin()).isEqualTo(LocalTime.of(17,00));

    }

    @DisplayName("Test para eliminar un horario por id")
    @Test
    void testDeleteHorario(){
        // given
        Medico medicoSave = medicoRepository.save(medicoFijo);
        HorarioDisponible horarioSave = horarioDisponibleRepository.save(horarioFijo);
        // when
        horarioDisponibleRepository.deleteById(horarioSave.getId());
        Optional<HorarioDisponible> horarioDisponibleOptional = horarioDisponibleRepository.findById(horarioSave.getId());
        // then
        assertThat(horarioDisponibleOptional).isEmpty();
    }
}
