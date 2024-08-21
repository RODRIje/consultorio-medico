package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MedicoRepositoryTests {
    @Autowired
    private MedicoRepository medicoRepository;

    private Medico medicoFijo;
    @BeforeEach
    void setup(){
        medicoFijo = Medico.builder()
                .nombre("Martin")
                .apellido("Faravelo")
                .email("martinfara@gmail.com")
                .especialidad("Clinico")
                .matricula("15ARG235ch")
                .build();
    }

    @DisplayName("Test para guardar un medico")
    @Test
    void testSaveMedico(){
        // given
        Medico medico = Medico.builder()
                .nombre("Cristian")
                .apellido("Ramirez")
                .email("crisram@gmail.com")
                .especialidad("Pediatra")
                .matricula("22URU447pl")
                .build();
        // when
        Medico medicoSave = medicoRepository.save(medico);
        // then
        assertThat(medicoSave).isNotNull();
        assertThat(medicoSave.getId()).isGreaterThan(0);
        assertThat(medicoSave.getNombre()).isEqualTo("Cristian");
    }
    @DisplayName("Test para traer todos los medicos")
    @Test
    void testFindAllMedicos(){
        // given
        Medico medicoNuevo = Medico.builder()
                .nombre("Cristian")
                .apellido("Ramirez")
                .email("crisram@gmail.com")
                .especialidad("Pediatra")
                .matricula("22URU447pl")
                .build();
        medicoRepository.save(medicoNuevo);
        medicoRepository.save(medicoFijo);
        // when
        List<Medico> medicoList = medicoRepository.findAll();
        // then
        assertThat(medicoList).isNotNull();
        assertThat(medicoList.size()).isEqualTo(6);
    }
    @DisplayName("Test para traer un medico por id")
    @Test
    void testListMedicoById(){
        // given
        medicoRepository.save(medicoFijo);
        // when
        Medico medico = medicoRepository.findById(medicoFijo.getId()).get();
        // then
        assertThat(medico).isNotNull();
        assertThat(medico.getNombre()).isEqualTo("Martin");
    }

    @DisplayName("Test para actualizar un medico")
    @Test
    void testUpdateMedico(){
        // given
        medicoRepository.save(medicoFijo);
        // when
        Medico medicoSave = medicoRepository.findById(medicoFijo.getId()).get();
        medicoSave.setNombre("Ignacio");
        medicoSave.setApellido("Del tero");
        medicoSave.setEmail("ignadeltero@gmail.com");
        Medico medicoUpdate = medicoRepository.save(medicoSave);
        // then
        assertThat(medicoUpdate).isNotNull();
        assertThat(medicoUpdate.getNombre()).isEqualTo("Ignacio");
        assertThat(medicoUpdate.getApellido()).isEqualTo("Del tero");
        assertThat(medicoUpdate.getEmail()).isEqualTo("ignadeltero@gmail.com");
    }

    @DisplayName("Test para eliminar un cliente")
    @Test
    void testDeleteMedico(){
        // given
        medicoRepository.save(medicoFijo);
        // when
        medicoRepository.deleteById(medicoFijo.getId());
        Optional<Medico> medicoOptional = medicoRepository.findById(medicoFijo.getId());
        // then
        assertThat(medicoOptional).isEmpty();
    }
}
